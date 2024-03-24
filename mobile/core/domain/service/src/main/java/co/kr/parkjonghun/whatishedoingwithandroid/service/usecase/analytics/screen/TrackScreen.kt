package co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.screen

import co.kr.parkjonghun.whatishedoingwithandroid.base.usecase.UseCase
import co.kr.parkjonghun.whatishedoingwithandroid.service.gateway.provider.AnalyticsProvider

/**
 * Report ScreenView to the outside.
 */
interface TrackScreen : UseCase {
    operator fun invoke(screenName: String)
}

class TrackScreenImpl(
    private val analyticsProvider: AnalyticsProvider,
) : TrackScreen {
    override operator fun invoke(screenName: String) {
        analyticsProvider.logScreen(screenName)
    }
}
