package io.jadu.nivi.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val blue = Color(0xFF3A7DFF)
val midnight = Color(0xFF161A3C)
val grape = Color(0xFF5A4BFF)
val nearBlack = Color(0xFF0D0F21)
// --------------------------------

@Composable
fun AnimatedLightBleedBackground(
    modifier: Modifier = Modifier,
    animationDurationMs: Int = 8000,
    bleedIntensity: Float = 0.15f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "lightBleedTransition")

    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMs,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "lightBleedProgress"
    )

    val darkColors = remember(bleedIntensity) {
        createDarkModeColors(bleedIntensity)
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        // Use the provided custom drawing logic
        drawNaturalLight(
            animationProgress = animationProgress,
            colors = darkColors
        )
    }
}

private fun createDarkModeColors(bleedIntensity: Float): List<Color> {
    return listOf(
        blue.copy(alpha = bleedIntensity * 1.8f),
        midnight.copy(alpha = bleedIntensity * 1.2f),
        grape.copy(alpha = bleedIntensity * 0.8f),
        midnight.copy(alpha = bleedIntensity * 0.6f),
        nearBlack.copy(alpha = bleedIntensity * 0.4f),
        Color.Transparent,
        Color.Transparent
    )
}

/**
 * Draws a natural, multi-source light bleed effect that
 * animates gently.
 */
private fun DrawScope.drawNaturalLight(
    animationProgress: Float,
    colors: List<Color>
) {
    val lightSourceX = size.width * 0.85f
    val lightSourceY = size.height * 0.15f

    // Calculate animated positions for the primary light source
    val animatedX = lightSourceX + cos(animationProgress * 2 * PI).toFloat() * size.width * 0.02f
    val animatedY = lightSourceY + sin(animationProgress * 2 * PI * 0.7f).toFloat() * size.height * 0.015f

    // Define multiple light sources for a more complex, natural feel
    val lightSources = listOf(
        Offset(animatedX, animatedY), // Primary source
        Offset(animatedX - size.width * 0.05f, animatedY + size.height * 0.03f), // Secondary
        Offset(animatedX + size.width * 0.03f, animatedY - size.height * 0.02f)  // Tertiary
    )

    lightSources.forEachIndexed { index, lightSource ->
        // Vary the radius for each light source
        val radiusMultiplier = when (index) {
            0 -> 1.2f // Main light is largest
            1 -> 0.8f
            else -> 0.6f
        }

        // Vary the intensity (alpha) for each light source
        val intensityMultiplier = when (index) {
            0 -> 1.0f // Main light is brightest
            1 -> 0.6f
            else -> 0.4f
        }

        val radius = size.width * radiusMultiplier
        // Apply the intensity multiplier to the base colors
        val naturalColors = colors.map { color ->
            color.copy(alpha = color.alpha * intensityMultiplier)
        }

        val brush = Brush.radialGradient(
            colors = naturalColors,
            center = lightSource,
            radius = radius
        )

        drawRect(
            brush = brush,
            size = size
        )
    }
}