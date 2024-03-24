package co.kr.parkjonghun.whatishedoingwithandroid.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive.WihdText
import co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive.WihdTextStyle
import co.kr.parkjonghun.whatishedoingwithandroid.service.usecase.analytics.screen.TrackScreen
import org.koin.compose.koinInject

const val profileScreenRoute = "profile"

fun NavGraphBuilder.profileScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    composable(profileScreenRoute) {
        ProfileScreen(
            modifier = modifier,
            contentPadding = contentPadding,
        )
    }
}

fun NavController.navigateToProfileScreen() {
    navigate(profileScreenRoute) {
        popUpTo(id = graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Suppress("UnusedParameter")
@Composable
fun ProfileScreen(
    // TODO state holder
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val trackScreen = koinInject<TrackScreen>()
    SideEffect {
        trackScreen("ProfileScreen")
    }

    ProfileBody()
}

@Composable
fun ProfileBody() {
    WihdText(
        text = "profile",
        style = WihdTextStyle.H1,
    )
}
