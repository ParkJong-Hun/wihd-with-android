package co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import co.kr.parkjonghun.whatishedoingwithandroid.system.extension.WihdPreview
import co.kr.parkjonghun.whatishedoingwithandroid.system.theme.dark_linkBlue
import co.kr.parkjonghun.whatishedoingwithandroid.system.theme.light_linkBlue

@Composable
fun PrimaryFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun SecondaryFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun TertiaryFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun LinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit),
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (isSystemInDarkTheme()) dark_linkBlue else light_linkBlue,
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@WihdPreview
@Composable
private fun PrimaryFilledButtonPreview() {
    PrimaryFilledButton(
        onClick = {},
    ) {
        Text("This is Preview Button.")
    }
}

@WihdPreview
@Composable
private fun SecondaryFilledButtonPreview() {
    SecondaryFilledButton(
        onClick = {},
    ) {
        Text("This is Preview Button.")
    }
}

@WihdPreview
@Composable
private fun TertiaryFilledButtonPreview() {
    TertiaryFilledButton(
        onClick = {},
    ) {
        Text("This is Preview Button.")
    }
}

@WihdPreview
@Composable
private fun LinkButtonPreview() {
    LinkButton(
        onClick = {},
    ) {
        Text("This is Preview Button.")
    }
}
