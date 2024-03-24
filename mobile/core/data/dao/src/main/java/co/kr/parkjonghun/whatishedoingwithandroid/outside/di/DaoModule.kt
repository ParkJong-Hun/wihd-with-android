package co.kr.parkjonghun.whatishedoingwithandroid.outside.di

import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.SupabaseDao
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.SupabaseDaoImpl
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.datastore.UserDataStoreDao
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.datastore.UserDataStoreDaoImpl
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.firebase.FirebaseDao
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.firebase.FirebaseDaoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val daoModule = module {
    single<UserDataStoreDao> {
        UserDataStoreDaoImpl()
    }

    single<SupabaseDao> {
        SupabaseDaoImpl()
    }

    single<FirebaseDao> {
        FirebaseDaoImpl(context = androidContext())
    }
}
