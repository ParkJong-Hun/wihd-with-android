package co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.di

import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.screen.TrackScreen
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.screen.TrackScreenImpl
import org.koin.dsl.module

internal val analyticsModule = module {
    single<TrackScreen> {
        TrackScreenImpl(
            analyticsProvider = get(),
        )
    }
}
