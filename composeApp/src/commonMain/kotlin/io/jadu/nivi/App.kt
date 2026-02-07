package io.jadu.nivi

/**
 * Created by Rohit (Jadu)
 * Sun 1 FEB
*/

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import io.jadu.nivi.presentation.screens.LoginScreen
import io.jadu.nivi.presentation.screens.OnboardingScreen
import io.jadu.nivi.presentation.theme.Nivi

@Composable
@Preview
fun App() {
    var showOnboarding by remember { mutableStateOf(true) }
    Nivi {
        if (showOnboarding) {
            OnboardingScreen(
                onFinishOnboarding = {
                    showOnboarding = false
                }
            )
        } else {
            LoginScreen()
        }
    }
}