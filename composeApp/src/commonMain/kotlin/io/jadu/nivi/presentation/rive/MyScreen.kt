package io.jadu.nivi.presentation.rive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.jadu.nivi.presentation.components.RiveAnimationComponent
import io.jadu.nivi.presentation.theme.Spacing

@Composable
fun MyScreen() {
    Column {
        // URL-based animation
//        RiveAnimationComponent(
//            source = "https://cdn.rive.app/animations/your_animation.riv",
//            modifier = Modifier.size(Spacing.s64 * 3) // 192.dp
//        )

        // Resource-based animation (file in resources/files directory)
        RiveAnimationComponent(
            source = "files/shapes.riv",
            modifier = Modifier.size(Spacing.s64 * 3) // 192.dp
        )
    }
}


@Preview
@Composable
fun PreviewRive() {
    MyScreen()
}