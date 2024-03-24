package co.kr.parkjonghun.whatishedoingwithandroid.component.atom.primitive

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.kr.parkjonghun.whatishedoingwithandroid.mobile.testing.captureMultiTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@LooperMode(LooperMode.Mode.PAUSED)
class IndicatorScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_ProgressCircle() {
        composeTestRule.captureMultiTheme("Indicator", "ProgressCircle") { _ ->
            Surface {
                ProgressCircle(
                    progress = 33,
                    modifier = Modifier.size(100.dp),
                )
            }
        }
    }

    @Test
    fun test_ProgressBar() {
        composeTestRule.captureMultiTheme("Indicator", "ProgressIndicator") { _ ->
            Surface {
                ProgressBar(
                    progress = 66,
                    modifier = Modifier.width(100.dp),
                )
            }
        }
    }

    @Test
    fun test_IndicatorCircle() {
        composeTestRule.captureMultiTheme("Indicator", "IndicatorCircle") { _ ->
            Surface {
                IndicatorBar(
                    isLoading = true,
                    modifier = Modifier.size(100.dp),
                )
            }
        }
    }

    @Test
    fun test_IndicatorBar() {
        composeTestRule.captureMultiTheme("Indicator", "IndicatorBar") { _ ->
            Surface {
                IndicatorBar(
                    isLoading = true,
                    modifier = Modifier.width(100.dp),
                )
            }
        }
    }
}
