package io.jadu.nivi.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.muazkadan.rivecmp.CustomRiveAnimation
import dev.muazkadan.rivecmp.RiveCompositionSpec
import dev.muazkadan.rivecmp.rememberRiveComposition
import dev.muazkadan.rivecmp.utils.ExperimentalRiveCmpApi
import nivi.composeapp.generated.resources.Res

/**
 * A reusable component that displays Rive animations from either a URL or a resource file.
 *
 * @param modifier Modifier to be applied to the animation
 * @param source The source of the Rive animation - can be a URL (starting with http:// or https://) or a resource file name
 * @param autoPlay Whether to automatically play the animation on load
 * @param loop Whether to loop the animation
 * @param stateMachineName Optional state machine name to trigger
 * @param animationName Optional animation name to play
 */
@OptIn(ExperimentalRiveCmpApi::class)
@Composable
fun RiveAnimationComponent(
    source: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    loop: Boolean = true,
    stateMachineName: String? = null,
    animationName: String? = null
) {
    val composition by rememberRiveComposition {
        if (source.startsWith("http://") || source.startsWith("https://")) {
            // URL-based composition
            RiveCompositionSpec.url(source)
        } else {
            // Resource-based composition - use the path as provided
            RiveCompositionSpec.byteArray(Res.readBytes(source))
        }
    }

    Box(modifier = modifier) {
        CustomRiveAnimation(
            modifier = Modifier.matchParentSize(),
            composition = composition
        )
    }
}
