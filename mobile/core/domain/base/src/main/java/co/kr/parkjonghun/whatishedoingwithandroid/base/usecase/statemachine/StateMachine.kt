package co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine

import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.UseCase
import co.kr.parkjonghun.whatishedoingwithandroid.base.util.Matcher
import kotlinx.coroutines.flow.SharedFlow
import kotlin.coroutines.CoroutineContext

/**
 * Create a [StateMachine].
 */
public fun <STATE : State, ACTION : Action> createStateMachine(
    name: String,
    initialState: STATE,
    coroutineContext: CoroutineContext? = null,
    sideEffectCreator: StateMachine.SideEffectCreator<out SideEffect<STATE, ACTION>, STATE, ACTION>,
    reactiveEffect: ReactiveEffect<STATE, ACTION>? = null,
    diagramBlock: StateMachine.DiagramBuilder<STATE, ACTION>.() -> Unit,
): StateMachine<STATE, ACTION> =
    StateMachineImpl(
        name = name,
        initialState = initialState,
        coroutineContext = coroutineContext,
        sideEffectCreator = sideEffectCreator,
        reactiveEffect = reactiveEffect,
        diagram = StateMachine.DiagramBuilder<STATE, ACTION>(initialState = initialState)
            .apply(diagramBlock)
            .build(),
    )

/**
 * Finite state machine.
 */
interface StateMachine<STATE : State, ACTION : Action> : UseCase {
    val currentState: STATE
    public val flow: SharedFlow<STATE>

    public fun dispatch(
        action: ACTION,
        after: (Transition<STATE, ACTION>) -> Unit = {},
    )

    /**
     *  An action performed when an action occurs in a certain [State].
     *  After an [Action] occurs, it always transitions to another [State].
     */
    interface SideEffect<STATE : State, ACTION : Action> {
        public suspend fun fire(
            targetStateMachine: StateMachine<STATE, ACTION>,
            validTransition: ValidTransition<STATE, ACTION>,
        )
    }

    /**
     * A condition specifier that specifies which [SideEffect] is triggered when a certain action is taken in a certain [State].
     */
    interface SideEffectCreator<SIDE_EFFECT : SideEffect<STATE, ACTION>, STATE : State, ACTION : Action> {
        fun create(state: STATE, action: ACTION): SIDE_EFFECT?
    }

    /**
     * This is a [SideEffect] that collects data by observing some flow.
     * then an [Action] occurs and transitions to another [State].
     */
    interface ReactiveEffect<STATE : State, ACTION : Action> {
        public suspend fun fire(targetStateMachine: StateMachine<STATE, ACTION>)
    }

    /**
     * Information from which [State] the action was taken and whether it was valid or not.
     */
    sealed interface Transition<STATE : State, ACTION : Action> {
        val fromState: STATE
        val targetAction: ACTION

        data class Valid<STATE : State, ACTION : Action>(
            override val fromState: STATE,
            val toState: STATE,
            override val targetAction: ACTION,
        ) : Transition<STATE, ACTION>

        data class Invalid<STATE : State, ACTION : Action>(
            override val fromState: STATE,
            override val targetAction: ACTION,
        ) : Transition<STATE, ACTION>
    }

    /**
     * Diagram of a state machine.
     */
    class Diagram<STATE : State, ACTION : Action>(
        val initialState: STATE,
        val fromStateMap: Map<Matcher<STATE, STATE>, FromState<STATE, ACTION>>,
    ) {
        class FromState<STATE : State, ACTION : Action> {
            class TransitionTo<STATE : State>(val toState: STATE)

            val transitionToStateMap =
                mutableMapOf<Matcher<ACTION, ACTION>, (STATE, ACTION) -> TransitionTo<STATE>>()
        }
    }

    /**
     * Creating a [Diagram] of each state machine.
     */
    class DiagramBuilder<SEALED_STATE : State, SEALED_ACTION : Action>(
        private val initialState: SEALED_STATE,
    ) {
        /**
         * Relationships between [State]s.
         */
        private val relationMap =
            LinkedHashMap<Matcher<SEALED_STATE, SEALED_STATE>, Diagram.FromState<SEALED_STATE, SEALED_ACTION>>()

        @Deprecated(message = "It is not for service.")
        fun <TARGET_STATE : SEALED_STATE> fromState(
            stateMatcher: Matcher<SEALED_STATE, TARGET_STATE>,
            config: RelationBuilder<TARGET_STATE>.() -> Unit,
        ) {
            relationMap[stateMatcher] = RelationBuilder<TARGET_STATE>().apply(config).build()
        }

        @Suppress("DEPRECATION")
        public inline fun <reified TARGET_STATE : SEALED_STATE> fromState(
            noinline config: RelationBuilder<TARGET_STATE>.() -> Unit,
        ) = fromState(Matcher.any(), config)

        fun build(): Diagram<SEALED_STATE, SEALED_ACTION> =
            Diagram(initialState, relationMap.toMap())

        /**
         * Creating specifications for what actions should be used when going from one state of each state machine to another.
         */
        inner class RelationBuilder<TARGET_STATE : SEALED_STATE> {
            private val fromStateFactor = Diagram.FromState<SEALED_STATE, SEALED_ACTION>()

            @Suppress("UNCHECKED_CAST")
            @Deprecated(message = "It is not for service.")
            fun <TARGET_ACTION : SEALED_ACTION> on(
                actionMatcher: Matcher<SEALED_ACTION, TARGET_ACTION>,
                transition: TARGET_STATE.(TARGET_ACTION) -> Diagram.FromState.TransitionTo<SEALED_STATE>,
            ) {
                fromStateFactor.transitionToStateMap[actionMatcher] = { state, action ->
                    transition(state as TARGET_STATE, action as TARGET_ACTION)
                }
            }

            @Suppress("DEPRECATION")
            public inline fun <reified TARGET_ACTION : SEALED_ACTION> on(
                noinline transition: TARGET_STATE.(TARGET_ACTION) -> Diagram.FromState.TransitionTo<SEALED_STATE>,
            ) {
                on(Matcher.any(), transition)
            }

            public fun TARGET_STATE.transitionTo(newState: SEALED_STATE) =
                Diagram.FromState.TransitionTo(newState)

            public fun TARGET_STATE.transitionToSelf(): Diagram.FromState.TransitionTo<TARGET_STATE> =
                Diagram.FromState.TransitionTo(this)

            fun build() = fromStateFactor
        }
    }
}

typealias Diagram<S, A> = StateMachine.Diagram<S, A>
typealias Transition<S, A> = StateMachine.Transition<S, A>
typealias ValidTransition<S, A> = StateMachine.Transition.Valid<S, A>
typealias InValidTransition<S, A> = StateMachine.Transition.Invalid<S, A>
typealias SideEffect<S, A> = StateMachine.SideEffect<S, A>
typealias ReactiveEffect<S, A> = StateMachine.ReactiveEffect<S, A>
