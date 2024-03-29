package co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.statemachine.app

import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.StateMachine
import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.statemachine.ValidTransition
import co.kr.parkjonghun.whatishedoingwithandroid.service.gateway.repository.UserRepository

sealed interface AppSideEffect : StateMachine.SideEffect<AppState, AppAction> {
    class GetUser(
        private val userRepository: UserRepository,
    ) : AppSideEffect {
        override suspend fun fire(
            targetStateMachine: StateMachine<AppState, AppAction>,
            validTransition: ValidTransition<AppState, AppAction>,
        ) {
            runCatching {
                userRepository.getUser()
            }
                .onSuccess { result ->
                    result?.let { user ->
                        targetStateMachine.dispatch(AppAction.AppAvailable(user))
                    } ?: let {
                        targetStateMachine.dispatch(AppAction.AppUnavailable)
                    }
                }
                .onFailure { throwable ->
                    targetStateMachine.dispatch(AppAction.Fail(throwable))
                }
        }
    }
}
