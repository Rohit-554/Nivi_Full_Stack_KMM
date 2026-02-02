package io.jadu.nivi.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Nivi(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTheme provides rememberThemeController()
    ) {
        val theme = LocalTheme.current // use this to toggle between light and dark
        val fontFamily = manropeFamily()
        val typography = Typography(
            bodySmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            bodyMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight(600),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            titleSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight(600),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            titleMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight(700),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            labelMedium = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            headlineSmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            headlineLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            displaySmall = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            labelLarge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight(700),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
        )

        // Dark mode only
        val colors = darkColorScheme(
            primary = MajorColors.TrustBlue.color,
            onPrimary = MajorColors.PureWhite.color,
            secondary =  MajorColors.SecureNavy.color,
            onSecondary =  MajorColors.PureWhite.color,
            tertiary =  MajorColors.GoldAccent.color,
            onTertiary =  MajorColors.DeepCharcoal.color,

            background =  ElementsColors.PrimaryBackground.color,
            onBackground =  ElementsColors.TextPrimary.color,
            surface =  ElementsColors.CardBackground.color,
            onSurface =  ElementsColors.TextPrimary.color,

            primaryContainer = Color(0xFF1E3A8A),
            onPrimaryContainer = Color(0xFF93C5FD),
            secondaryContainer = Color(0xFF374151),
            onSecondaryContainer =  ElementsColors.TextSecondary.color,

            error =  ElementsColors.NegativeRed.color,
            onError =  MajorColors.PureWhite.color,
            errorContainer =  ElementsColors.ErrorBackground.color,
            onErrorContainer =  ElementsColors.NegativeRed.color,

            outline =  ElementsColors.BorderMedium.color,
            outlineVariant =  ElementsColors.BorderLight.color,
            surfaceVariant =  ElementsColors.SectionBackground.color,
            onSurfaceVariant =  ElementsColors.TextSecondary.color,

            inverseSurface =  MajorColors.PureWhite.color,
            inverseOnSurface =  MajorColors.DeepCharcoal.color,
            inversePrimary =  MajorColors.TrustBlue.color,

            surfaceContainer =  ElementsColors.CardBackground.color,
            surfaceTint =  MajorColors.TrustBlue.color,
            scrim = ElementsColors.ScrimColor.color
        )

        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}