package io.jadu.nivi.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.utils.squircleShape.SquircleShape
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    imageRes: DrawableResource? = null,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: DrawableResource? = null,
    error: DrawableResource? = null,
    onImageLoaded: () -> Unit = {},
    glow: Boolean = false,
    colorFilter: ColorFilter? = null,
    shape: Shape = SquircleShape(32f)
) {
    val imageLoaded = remember { mutableStateOf(false) }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (imageLoaded.value) 1f else 0f, animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier.clip(shape).then(
                if (glow) Modifier.glow(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), radius = Spacing.s20
                ) else Modifier
            )
    ) {
        when {
            imageUrl != null -> {
                val painter =
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalPlatformContext.current).data(imageUrl)
                            .listener(onStart = {
                                println("ImageLoading Started loading: $imageUrl")
                            }, onSuccess = { _, _ ->
                                println("ImageLoading Image loaded successfully")
                            }, onError = { _, result ->
                                println("ImageLoading Image load failed")
                            }).build(), onSuccess = { _ ->
                            imageLoaded.value = true
                            onImageLoaded()
                        })

                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize().alpha(animatedAlpha),
                    contentScale = contentScale,
                    colorFilter = colorFilter
                )

                if (!imageLoaded.value) {
                    if (placeholder != null) {
                        Image(
                            painter = painterResource(placeholder),
                            contentDescription = "Placeholder",
                            modifier = Modifier.fillMaxSize().shimmer(
                                    customShimmer = rememberShimmer(
                                        shimmerBounds = ShimmerBounds.View,
                                        theme = ShimmerTheme(
                                            shaderColors = listOf(
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                            ),
                                            shaderColorStops = listOf(0.0f, 0.5f),
                                            blendMode = BlendMode.SrcOver,
                                            rotation = 0f,
                                            shimmerWidth = Spacing.s2,
                                            animationSpec = infiniteRepeatable(
                                                animation = shimmerSpec(
                                                    durationMillis = 800,
                                                    easing = LinearOutSlowInEasing,
                                                    delayMillis = 1_500,
                                                ),
                                                repeatMode = RepeatMode.Restart,
                                            ),
                                        ),
                                    )
                                )
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize().shimmer(
                                    customShimmer = rememberShimmer(
                                        shimmerBounds = ShimmerBounds.View,
                                        theme = ShimmerTheme(
                                            shaderColors = listOf(
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                            ),
                                            shaderColorStops = listOf(0.0f, 0.5f),
                                            blendMode = BlendMode.SrcOver,
                                            rotation = 0f,
                                            shimmerWidth = Spacing.s2,
                                            animationSpec = infiniteRepeatable(
                                                animation = shimmerSpec(
                                                    durationMillis = 800,
                                                    easing = LinearOutSlowInEasing,
                                                    delayMillis = 1_500,
                                                ),
                                                repeatMode = RepeatMode.Restart,
                                            ),
                                        ),
                                    )
                                )
                        )
                    }
                }
            }

            imageRes != null -> {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize().alpha(1f),
                    contentScale = contentScale,
                    colorFilter = colorFilter
                )
            }

            else -> {
                error?.let {
                    Image(
                        painter = painterResource(it),
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize().shimmer(),
                        contentScale = contentScale,
                        colorFilter = colorFilter
                    )
                } ?: Box(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.errorContainer).padding(Spacing.s16),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = "Error loading image",
                        tint = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.size(Spacing.s48)
                    )
                }
            }
        }
    }
}

fun Modifier.glow(color: Color, radius: Dp): Modifier = this.drawBehind {
    drawRect(
        color = color, size = size, style = Fill, alpha = 0.6f
    )
}