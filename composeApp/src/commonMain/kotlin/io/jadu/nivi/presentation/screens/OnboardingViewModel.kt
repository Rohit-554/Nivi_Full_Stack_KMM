package io.jadu.nivi.presentation.screens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.nivi.domain.manager.AuthManager
import io.jadu.nivi.presentation.navigation.AppRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class OnboardingViewModel(
    val prefs: DataStore<Preferences>,
    val authManager: AuthManager
) : ViewModel() {
    private val onboardingPages = listOf(
        OnboardingPage(
            title = "Track Your Expenses",
            description = "Keep track of all your expenses in one place with AI-powered categorization and insights.",
            riveAnimationUrl = "files/shapes.riv"
        ),
        OnboardingPage(
            title = "Smart Budgeting",
            description = "Set budgets and get intelligent recommendations to help you save more and spend wisely.",
            riveAnimationUrl = "files/moving_shape.riv"
        ),
        OnboardingPage(
            title = "Financial Insights",
            description = "Get personalized financial insights and predictions powered by AI to achieve your goals.",
            riveAnimationUrl = "files/shapes_2.riv"
        )
    )

    private val _uiState = MutableStateFlow(
        OnboardingUiState(
            pages = onboardingPages,
            totalPages = onboardingPages.size
        )
    )
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun updateCurrentPage(page: Int) {
        _uiState.value = _uiState.value.copy(
            currentPage = page,
            isLastPage = page == _uiState.value.totalPages - 1
        )
    }

    fun nextPage(onFinish: () -> Unit) {
        viewModelScope.launch {
            if (_uiState.value.isLastPage) {
                onFinish()
            }
        }
    }

    private val isNewUserKey = booleanPreferencesKey("is_new_user")

    suspend fun getCurrentRoute(): AppRoute {
        val isLoggedIn = authManager.isLoggedIn()
        if (isLoggedIn) {
            return AppRoute.Home
        }

        val isNewUser = prefs.data.first()[isNewUserKey] ?: true
        return if (isNewUser) AppRoute.OnBoarding.OnBoarding1 else AppRoute.OnBoarding.Login
    }

    suspend fun setUserStatus(isFirstTime: Boolean): Boolean {
        prefs.edit { it[isNewUserKey] = isFirstTime }
        return true
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val riveAnimationUrl: String
)

data class OnboardingUiState(
    val currentPage: Int = 0,
    val pages: List<OnboardingPage> = emptyList(),
    val totalPages: Int = 0,
    val isLastPage: Boolean = false
)


