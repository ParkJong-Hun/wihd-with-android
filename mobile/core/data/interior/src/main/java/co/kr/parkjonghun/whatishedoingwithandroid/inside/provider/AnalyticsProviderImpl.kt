package co.kr.parkjonghun.whatishedoingwithandroid.inside.provider

import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.firebase.FirebaseDao
import co.kr.parkjonghun.whatishedoingwithandroid.service.gateway.provider.AnalyticsProvider

class AnalyticsProviderImpl(
    private val firebaseDao: FirebaseDao,
) : AnalyticsProvider {
    override fun logEvent(name: String, params: Map<String, Any>) {
        firebaseDao.logEvent(name, params)
    }

    override fun logScreen(screenName: String) {
        firebaseDao.logScreen(screenName)
    }
}
