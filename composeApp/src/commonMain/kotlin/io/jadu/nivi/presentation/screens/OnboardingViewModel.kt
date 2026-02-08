package io.jadu.nivi.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel : ViewModel() {
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


