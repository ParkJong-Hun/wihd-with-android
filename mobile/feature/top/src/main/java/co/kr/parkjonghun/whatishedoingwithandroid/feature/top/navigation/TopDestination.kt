package co.kr.parkjonghun.whatishedoingwithandroid.feature.top.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import co.kr.parkjonghun.whatishedoingwithandroid.news.NewsString
import co.kr.parkjonghun.whatishedoingwithandroid.post.PostString
import co.kr.parkjonghun.whatishedoingwithandroid.profile.ProfileString

@Immutable
enum class TopDestination(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val iconTextRes: Int,
    @StringRes val labelTextRes: Int,
) {
    NEWS(
        icon = Icons.AutoMirrored.Outlined.List,
        selectedIcon = Icons.AutoMirrored.Rounded.List,
        iconTextRes = NewsString.news_description,
        labelTextRes = NewsString.news_label,
    ),
    POST(
        icon = Icons.Outlined.AddCircle,
        selectedIcon = Icons.Rounded.AddCircle,
        iconTextRes = PostString.post_description,
        labelTextRes = PostString.post_label,
    ),
    PROFILE(
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Rounded.Person,
        iconTextRes = ProfileString.profile_description,
        labelTextRes = ProfileString.profile_label,
    ),
}
