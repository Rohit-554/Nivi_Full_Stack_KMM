package io.jadu.nivi.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class ThemeController(
    val isDark: Boolean,
    val toggle: () -> Unit
)

val LocalTheme = compositionLocalOf<ThemeController> {
    error("No ThemeController provided")
}

@Composable
fun rememberThemeController(): ThemeController {
    val systemDark = isSystemInDarkTheme()

    var isDark by remember { mutableStateOf(systemDark) }

    return remember(isDark) {
        ThemeController(
            isDark = isDark,
            toggle = { !isDark }
        )
    }
}