package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DriftDarkColorScheme = darkColorScheme(
    primary = DriftWhite,
    onPrimary = DriftBlack,
    primaryContainer = DriftCardDark,
    onPrimaryContainer = DriftWhite,
    secondary = DriftEmerald,
    onSecondary = DriftBlack,
    background = DriftBlack,
    onBackground = DriftWhite,
    surface = DriftSurface,
    onSurface = DriftWhite,
    surfaceVariant = DriftCardDark,
    onSurfaceVariant = DriftSubtle,
    outline = DriftCardBorder
)

private val DriftLightColorScheme = lightColorScheme(
    primary = DriftBlack,
    onPrimary = DriftWhite,
    primaryContainer = DriftOffWhite,
    onPrimaryContainer = DriftBlack,
    secondary = DriftEmerald,
    onSecondary = DriftWhite,
    background = DriftOffWhite,
    onBackground = DriftBlack,
    surface = DriftWhite,
    onSurface = DriftBlack,
    surfaceVariant = DriftOffWhite,
    onSurfaceVariant = DriftMuted,
    outline = DriftLightBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent street aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DriftDarkColorScheme else DriftLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DriftTypography,
        content = content
    )
}
