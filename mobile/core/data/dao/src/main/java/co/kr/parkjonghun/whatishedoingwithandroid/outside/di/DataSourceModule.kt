package co.kr.parkjonghun.whatishedoingwithandroid.outside.di

import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.FakePreferencesDataSourceImpl
import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.FakeRemoteDataSourceImpl
import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.PreferencesDataSource
import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.PreferencesDataSourceImpl
import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.RemoteDataSource
import co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource.RemoteDataSourceImpl
import org.koin.dsl.module

internal val dataSourceModule = module {
    single<PreferencesDataSource> {
        PreferencesDataSourceImpl(
            userDataStoreDao = get(),
        )
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            firebaseDao = get(),
            supabaseDao = get(),
        )
    }
}

internal val fakeDataSourceModule = module {
    single<PreferencesDataSource> {
        FakePreferencesDataSourceImpl()
    }

    single<RemoteDataSource> {
        FakeRemoteDataSourceImpl()
    }
}
