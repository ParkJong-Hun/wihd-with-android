package co.kr.parkjonghun.whatishedoingwithandroid.mobile.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return
        with(FirebaseCrashlytics.getInstance()) {
            log(message)
            t?.let { FirebaseCrashlytics.getInstance().recordException(it) }
        }
    }
}
