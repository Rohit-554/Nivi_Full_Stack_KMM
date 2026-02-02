package io.jadu.nivi.presentation.utils.extensions

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics

fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
): Modifier = composed {
    val localIndication = interactionSource ?: remember { MutableInteractionSource() }
    
    clickable(
        indication = null,
        interactionSource = localIndication,
        onClick = onClick
    )
}

fun Modifier.bounceClickable(
    pressedScale: Float = 0.9f,
    enabled: Boolean = true,
    onLongPress: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed {
    val currentOnClick by rememberUpdatedState(onClick)
    val currentOnLongPress by rememberUpdatedState(onLongPress)
    
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        label = "BounceAnimation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .semantics { 
            role = Role.Button 
        }
        .pointerInput(enabled) {
            if (!enabled) return@pointerInput
            
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease() 
                    isPressed = false
                },
                onLongPress = {
                    currentOnLongPress?.invoke()
                },
                onTap = {
                    currentOnClick?.invoke()
                }
            )
        }
}