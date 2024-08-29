package payback.pixabay.challenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColors(
    primary = DarkBlue,
    onPrimary = White,

    background = BluishBlack,
    onBackground = White
)

private val LightColorPalette = lightColors(
    primary = Blue,
    onPrimary = White,

    background = White,
    onBackground = BluishBlack
)

@Composable
fun PaybackChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}