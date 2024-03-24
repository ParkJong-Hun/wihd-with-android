package co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.di

import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.di.analyticsModule
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.statemachine.di.stateMachineModule
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.validator.di.validatorModule
import org.koin.dsl.module

val useCaseModule = module {
    includes(
        stateMachineModule,
        validatorModule,
        analyticsModule,
    )
}
