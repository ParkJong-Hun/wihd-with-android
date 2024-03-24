package co.kr.parkjonghun.whatishedoingwithandroid.mobile

import android.app.Application
import co.kr.parkjonghun.whatishedoingwithandroid.inside.di.providerModule
import co.kr.parkjonghun.whatishedoingwithandroid.inside.di.repositoryModule
import co.kr.parkjonghun.whatishedoingwithandroid.mobile.di.dispatchersModule
import co.kr.parkjonghun.whatishedoingwithandroid.outside.di.dataModule
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WihdApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WihdApplication)
            modules(
                dispatchersModule,
                dataModule,
                repositoryModule,
                providerModule,
                useCaseModule,
            )
        }
    }
}
