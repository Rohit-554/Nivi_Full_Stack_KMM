package io.jadu.nivi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.jadu.nivi.presentation.navigation.AppRoute
import io.jadu.nivi.presentation.screens.OnboardingViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        var keepSplashScreen = true
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                keepSplashScreen
            }
        }

        setContent {
            var startRoute by remember { mutableStateOf<AppRoute?>(null) }

            LaunchedEffect(Unit) {
                startRoute = onboardingViewModel.getCurrentRoute()
                keepSplashScreen = false
            }

            startRoute?.let { route ->
                App(startRoute = route)
            }
        }
    }
}