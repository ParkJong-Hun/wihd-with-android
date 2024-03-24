package co.kr.parkjonghun.whatishedoingwithandroid.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive.PrimaryFilledButton
import co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive.WihdText
import co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive.WihdTextStyle
import co.kr.parkjonghun.whatishedoingwithandroid.component.molecule.custom.LoadingMask
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.screen.TrackScreen
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    val trackScreen = koinInject<TrackScreen>()
    SideEffect {
        trackScreen("LoginScreen")
    }

    val (state, intent) = rememberLoginProcessor()

    LoginBody(
        isShowError = state.isShowError to state.error,
        isShowLoading = state.isShowLoading,
        onClickLogin = intent::login,
        onClickErrorOk = intent::confirmErrorDialog,
        modifier = modifier,
    )
}

@Composable
private fun LoginBody(
    isShowError: Pair<Boolean, Throwable?>,
    isShowLoading: Boolean,
    onClickLogin: () -> Unit,
    onClickErrorOk: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Row {
            // TODO design it
            PrimaryFilledButton(onClick = onClickLogin) {
                WihdText(
                    text = "Login",
                    style = WihdTextStyle.L2,
                )
            }
        }

        LoadingMask(
            isLoading = isShowLoading,
            modifier = Modifier.fillMaxSize(),
        )

        // TODO handle error
        if (isShowError.first) {
            // TODO use Dialog component
            Dialog(onDismissRequest = onClickErrorOk) {
                WihdText(
                    text = "error occurs\n${isShowError.second}",
                    style = WihdTextStyle.B2,
                )
            }
        }
    }
}
