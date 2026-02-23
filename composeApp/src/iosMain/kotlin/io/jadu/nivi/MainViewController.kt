package io.jadu.nivi

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import io.jadu.nivi.presentation.navigation.AppRoute
import io.jadu.nivi.presentation.screens.OnboardingViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ViewControllerHelper : KoinComponent {
    val onboardingViewModel: OnboardingViewModel by inject()
}

fun MainViewController() = ComposeUIViewController {
    var startRoute by remember { mutableStateOf<AppRoute?>(null) }

    LaunchedEffect(Unit) {
        startRoute = ViewControllerHelper.onboardingViewModel.getCurrentRoute()
    }

    startRoute?.let {
        App(startRoute = it)
    }
}
