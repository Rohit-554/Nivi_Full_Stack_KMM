package io.jadu.nivi.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.theme.ElementsColors
import io.jadu.nivi.presentation.theme.MajorColors

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    blurRadius: Dp = 12.dp,
    backgroundAlpha: Float = 0.15f,
    borderAlpha: Float = 0.3f,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    val surfaceColor = MajorColors.TrustBlue.color
    val borderColor = ElementsColors.BorderLight.color

    Box(modifier = modifier.clip(shape)) {
        // Background layer with blur
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(radius = blurRadius)
                .background(brush = createGlassGradient(surfaceColor, backgroundAlpha))
                .border(
                    width = borderWidth,
                    brush = createGlassBorderGradient(borderColor, borderAlpha),
                    shape = shape
                )
        )
        
        // Content layer stays sharp
        content()
    }
}

private fun createGlassGradient(baseColor: Color, alpha: Float): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            baseColor.copy(alpha = alpha * 1.1f),  // Slightly brighter at top
            baseColor.copy(alpha = alpha),         // Base opacity
            baseColor.copy(alpha = alpha)          // Consistent bottom
        )
    )
}

private fun createGlassBorderGradient(borderColor: Color, alpha: Float): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            borderColor.copy(alpha = alpha * 1.5f),  // Strong highlight
            borderColor.copy(alpha = alpha * 0.3f),  // Fade in middle
            borderColor.copy(alpha = alpha * 0.8f)   // Subtle bottom
        )
    )
}