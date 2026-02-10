package io.jadu.nivi.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {

    @Serializable
    data object OnBoarding: AppRoute, NavKey {
        @Serializable
        data object Login: AppRoute, NavKey

        @Serializable
        data object Signup : AppRoute, NavKey

        @Serializable
        data object OnBoarding1 : AppRoute, NavKey

        @Serializable
        data object OnBoarding2 : AppRoute, NavKey

        @Serializable
        data object Onboarding3 : AppRoute, NavKey

    }

    @Serializable
    data object Home: AppRoute, NavKey {
        @Serializable
        data object Dashboard : AppRoute, NavKey
        @Serializable
        data object Profile : AppRoute, NavKey
        @Serializable
        data object Settings : AppRoute, NavKey
    }
}

