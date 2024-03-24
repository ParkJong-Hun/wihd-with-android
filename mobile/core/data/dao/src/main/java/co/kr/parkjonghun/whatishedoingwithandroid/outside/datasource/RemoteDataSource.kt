package co.kr.parkjonghun.whatishedoingwithandroid.outside.datasource

import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.SupabaseDao
import co.kr.parkjonghun.whatishedoingwithandroid.outside.dao.firebase.FirebaseDao
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

interface RemoteDataSource {
    val currentUser: Flow<UserInfo?>
    suspend fun login()
    suspend fun logout()
}

internal class RemoteDataSourceImpl(
    private val supabaseDao: SupabaseDao,
    private val firebaseDao: FirebaseDao,
) : RemoteDataSource {
    override val currentUser: Flow<UserInfo?> = flow {
        while (true) {
            val currentUser = supabaseDao.getUser()
            currentUser?.let { firebaseDao.setUserId(it.id) }
            emit(currentUser)
            delay(GET_USER_DELAY_MILLIS)
        }
    }

    override suspend fun login() {
        runCatching {
            supabaseDao.signInWithGithub()
        }.onSuccess {
            firebaseDao.logLogin()
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }
    }

    override suspend fun logout() = supabaseDao.signOut()
}

internal class FakeRemoteDataSourceImpl : RemoteDataSource {
    override val currentUser: Flow<UserInfo?> = flow {
        while (true) {
            // logged in status
            emit(FAKE_USER)
            // log out status
            // emit(null)
            delay(GET_USER_DELAY_MILLIS)
        }
    }

    override suspend fun login() {
        // do nothing
    }

    override suspend fun logout() {
        // do nothing
    }

    private companion object {
        private val FAKE_USER = UserInfo(
            aud = "fake_aud",
            email = "fake_email_1@email.com",
            factors = emptyList(),
            id = "f1a2k3e4i5d6",
        )
    }
}

private const val GET_USER_DELAY_MILLIS = 1000L
