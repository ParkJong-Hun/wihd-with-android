package co.kr.parkjonghun.whatishedoingwithandroid.service.gateway.provider

interface AnalyticsProvider {
    fun logEvent(name: String, params: Map<String, Any>)
    fun logScreen(screenName: String)
}
