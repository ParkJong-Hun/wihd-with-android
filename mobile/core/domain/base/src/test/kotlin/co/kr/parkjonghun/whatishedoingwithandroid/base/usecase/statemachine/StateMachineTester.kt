package co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
interface StateMachineTester<STATE : State, ACTION : Action> {
    private fun asserter(): StateMachineAsserter<STATE, ACTION> = StateMachineAsserterImpl()

    fun testCoroutineContext(): CoroutineContext = UnconfinedTestDispatcher()

    /**
     * this is the [StateMachine.SideEffectCreator] will be used in [testDispatch].
     */
    fun targetSideEffectCreator(): StateMachine.SideEffectCreator<out SideEffect<STATE, ACTION>, STATE, ACTION>

    /**
     * this is the [StateMachine.ReactiveEffect] will be used in [testDispatch].
     */
    fun targetReactiveEffect(): ReactiveEffect<STATE, ACTION>? = null

    /**
     * [StateMachine.Transition] validations and [StateMachine.SideEffectCreator]s are checked
     * to ensure that the both [afterState] and [sideEffect] are as expected.
     *
     * @param action target action to be dispatched
     * @param beforeState target state before dispatching action
     * @param afterState expected state after dispatching action,
     * null is meaning beforeState equals afterState
     * @param sideEffect expected sideEffect to be fired
     * @param targetStateMachine target state machine to be tested
     * */
    fun testDispatch(
        action: ACTION,
        beforeState: STATE,
        afterState: STATE?,
        sideEffect: SideEffect<STATE, ACTION>?,
        targetStateMachine: StateMachine<STATE, ACTION>,
    ) = runTest(context = testCoroutineContext()) {
        with(asserter()) {
            var transitionResult: Result<Unit?>? = null
            var sideEffectResult: Result<Unit?>? = null
            launch {
                targetStateMachine.flow.assertState(
                    beforeState = beforeState,
                    afterState = afterState.takeUnless { afterState -> afterState == beforeState },
                )
            }
            targetStateMachine.dispatch(action) { after ->
                transitionResult = runCatching {
                    if (afterState != null) {
                        val valid = true
                        assertTransition(
                            expectedValidation = valid,
                            actual = after,
                        )
                    } else {
                        val invalid = false
                        assertTransition(
                            expectedValidation = invalid,
                            actual = after,
                        )
                    }
                }
                sideEffectResult = runCatching {
                    assertSideEffect(
                        state = beforeState,
                        action = action,
                        expected = sideEffect,
                        creator = targetSideEffectCreator(),
                    )
                }
            }
            transitionResult?.getOrThrow() to sideEffectResult?.getOrThrow()
        }
    }
}
