package co.kr.parkjonghun.whatishedoingwithandroid.inside.di

import co.kr.parkjonghun.whatishedoingwithandroid.inside.provider.AnalyticsProviderImpl
import co.kr.parkjonghun.whatishedoingwithandroid.service.gateway.provider.AnalyticsProvider
import org.koin.dsl.module

val providerModule = module {
    single<AnalyticsProvider> {
        AnalyticsProviderImpl(
            firebaseDao = get(),
        )
    }
}
