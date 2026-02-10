package io.jadu.nivi.presentation.components.lottie

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.ExperimentalCompottieApi
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.dynamic.LottieDynamicProperties
import io.github.alexzhirkevich.compottie.dynamic.rememberLottieDynamicProperties
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import nivi.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @param path The path to the Lottie file (e.g., "files/lottie/animation.json or .lottie")
 * @param speed The speed multiplier for the animation playback
 * @param iteration The number of times the animation should repeat. Default value is infinite
 * @param isPlaying Boolean flag indicating whether the animation should be playing
 * @param modifier Modifier to be applied to the animation
 */

@OptIn(ExperimentalResourceApi::class, ExperimentalCompottieApi::class)
@Composable
fun LottieAnimation(
    path: String,
    modifier: Modifier = Modifier,
    speed: Float = 1f,
    iteration: Int = Compottie.IterateForever,
    isPlaying: Boolean = true,
    dynamicProperties: LottieDynamicProperties = rememberLottieDynamicProperties { }
) {
    val composition by rememberLottieComposition(path) {
        if (path.endsWith(".json"))
            LottieCompositionSpec.JsonString(
                Res.readBytes(path).decodeToString()
            ) else {
            LottieCompositionSpec.DotLottie(
                Res.readBytes(path)
            )
        }
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iteration,
        speed = speed,
        isPlaying = isPlaying
    )

    Image(
        painter = rememberLottiePainter(
            composition = composition,
            progress = { progress },
            dynamicProperties = dynamicProperties,
            enableMergePaths = true
        ),
        contentDescription = "Displaying Lottie animation",
        modifier = modifier,
    )
}