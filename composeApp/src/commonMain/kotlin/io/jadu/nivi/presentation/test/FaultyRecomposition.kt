package io.jadu.nivi.presentation.test

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.theme.bodyLarge

@Composable
fun TestScreens() {
    //OversharingParentDemo()
    //MissingKeysDemo()
    //DerivedStateDemo()
    PhaseSkippingDemo()
}
@Composable
fun PhaseSkippingDemo() {
    // 1. Set up an infinite animation (0f to 200f)
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Look at Layout Inspector Counts!", style = bodyLarge())

        Spacer(modifier = Modifier.height(20.dp))

//        // --- That's wrong dude ---
        Text("Bad Code (Red): Recomposes every frame")
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = animatedOffset.dp)
                .background(Color.Red)
        ) {
            Text("Recomposing...", color = Color.White, modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- Yeah see here
//        Text("Good Code (Green): Skips Composition")
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .offset { IntOffset(animatedOffset.roundToInt(), 0) }
//                .background(Color.Green)
//        ) {
//            Text("Skipping!", color = Color.Black, modifier = Modifier.align(Alignment.Center))
//        }
    }
}