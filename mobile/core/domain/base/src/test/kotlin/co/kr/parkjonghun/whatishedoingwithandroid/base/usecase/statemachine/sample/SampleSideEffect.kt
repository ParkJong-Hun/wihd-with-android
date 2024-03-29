package co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.sample

import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.SideEffect
import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.StateMachine
import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.ValidTransition

data object SampleSideEffect : SideEffect<SampleState, SampleAction> {
    override suspend fun fire(
        targetStateMachine: StateMachine<SampleState, SampleAction>,
        validTransition: ValidTransition<SampleState, SampleAction>,
    ) {
        runCatching { "Sample Data" }
            .onSuccess { targetStateMachine.dispatch(SampleAction.Succeed(it)) }
            .onFailure { targetStateMachine.dispatch(SampleAction.Fail(it)) }
    }
}
