package co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine

import android.util.Log
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext

@Suppress("UnusedPrivateProperty")
internal class StateMachineImpl<STATE : State, ACTION : Action>(
    private val name: String,
    initialState: STATE,
    coroutineContext: CoroutineContext?,
    private val sideEffectCreator: StateMachine.SideEffectCreator<out SideEffect<STATE, ACTION>, STATE, ACTION>,
    reactiveEffect: ReactiveEffect<STATE, ACTION>?,
    private val diagram: Diagram<STATE, ACTION>,
) : StateMachine<STATE, ACTION> {
    private val _flow = MutableSharedFlow<STATE>(replay = 1).also { it.tryEmit(initialState) }
    private val MutableSharedFlow<STATE>.value get() = replayCache.first()

    override val currentState: STATE get() = _flow.value
    override val flow: SharedFlow<STATE> = _flow

    private val stateMachineContext: CoroutineContext =
        CoroutineName(name) + (coroutineContext ?: (SupervisorJob() + Dispatchers.Default))
    private val stateMachineScope: CoroutineScope = CoroutineScope(stateMachineContext)

    private val sideEffectContext: CoroutineContext =
        CoroutineName("$name-sideEffect") + stateMachineContext
    private val sideEffectScope: CoroutineScope = CoroutineScope(sideEffectContext)

    private val mutex = Mutex()

    private val stateMachineJob: CompletableJob = SupervisorJob(stateMachineContext[Job])

    init {
        reactiveEffect?.let { sideEffectLaunch { it.fire(this@StateMachineImpl) } }
    }

    override fun dispatch(
        action: ACTION,
        after: (Transition<STATE, ACTION>) -> Unit,
    ) {
        stateMachineLaunch { after(transition(action)) }
    }

    private suspend fun transition(action: ACTION): Transition<STATE, ACTION> =
        mutex.withLock {
            _flow.value.let { currentState ->
                diagram.fromStateMap
                    .filterKeys { it.matchAll(currentState) }
                    .values
                    .flatMap { it.transitionToStateMap.entries }
                    .find { it.key.matchAll(action) }
                    ?.let {
                        ValidTransition(
                            fromState = currentState,
                            toState = it.value(currentState, action).toState,
                            targetAction = action,
                        )
                    }
                    ?.also { if (_flow.value != it.toState) _flow.emit(it.toState) }
                    ?: InValidTransition(
                        fromState = currentState,
                        targetAction = action,
                    )
            }
        }.also { transition -> checkFireSideEffect(action, transition) }

    private suspend fun checkFireSideEffect(
        action: ACTION,
        transition: Transition<STATE, ACTION>,
    ) {
        stateMachineLaunch {
            sideEffectLaunch {
                Log.v("SideEffect", "$name ${EMOJI}Before State: ${transition.fromState}")
                (transition as? ValidTransition<STATE, ACTION>)
                    ?.let { validTransition ->
                        Log.v(
                            "SideEffect",
                            "$name $EMOJI  called \"${transition.targetAction}\" action is 【VALID】",
                        )
                        sideEffectCreator.create(transition.fromState, action)
                            ?.fire(
                                targetStateMachine = this@StateMachineImpl,
                                validTransition = validTransition,
                            )
                        if (isTerminalState(validTransition.toState)) shutdown()
                    }
                    ?: run {
                        Log.w(
                            "SideEffect",
                            "$name $EMOJI  called \"${transition.targetAction}\" action is 【INVALID】 from ${transition.fromState}.",
                        )
                    }
                Log.v(
                    "SideEffect",
                    "$name ${EMOJI}After State: $currentState",
                )
            }
        }
    }

    private fun stateMachineLaunch(block: suspend CoroutineScope.() -> Unit) =
        stateMachineScope.launch { block() }

    private fun sideEffectLaunch(block: suspend CoroutineScope.() -> Unit) =
        sideEffectScope.launch { block() }

    private fun isTerminalState(state: STATE): Boolean =
        diagram.fromStateMap
            .filterKeys { it.matchAll(state) }
            .values
            .flatMap { it.transitionToStateMap.entries }
            .isEmpty()

    private fun shutdown() {
        stateMachineJob.cancel()
    }

    companion object {
        private const val EMOJI = "\uD83C\uDFAC"
    }
}
