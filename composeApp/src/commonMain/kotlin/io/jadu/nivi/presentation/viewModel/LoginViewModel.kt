package io.jadu.nivi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.nivi.domain.manager.AuthManager
import io.jadu.nivi.domain.useCase.LoginUseCase
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            val current = currentState.loginRequest ?: LoginRequest("", "")
            val updatedRequest = current.copy(email = email)
            currentState.copy(
                loginRequest = updatedRequest,
                isEnabled = updatedRequest.email.isNotBlank() && updatedRequest.password.isNotBlank()
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            val current = currentState.loginRequest ?: LoginRequest("", "")
            val updatedRequest = current.copy(password = password)
            currentState.copy(
                loginRequest = updatedRequest,
                isEnabled = updatedRequest.email.isNotBlank() && updatedRequest.password.isNotBlank()
            )
        }
    }

    fun login() {
        val request = _uiState.value.loginRequest ?: LoginRequest("", "")
        val sanitizedRequest = request.copy(
            email = request.email.trim().lowercase(),
            password = request.password.trim()
        )

        val validationError = validateCredentials(sanitizedRequest)
        if (validationError != null) {
            viewModelScope.launch { _event.emit(LoginEvent.Error(validationError)) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

    viewModelScope.launch {
        when (val result = loginUseCase(sanitizedRequest)) {
            is NetworkResult.Success -> {
                _uiState.update { it.copy(isLoading = false) }
                // Save auth data for persistent login
                result.data?.let { authResponse ->
                    authManager.saveAuthData(authResponse.token, authResponse.userName)
                }
                _event.emit(LoginEvent.Success(result.data))
            }
            is NetworkResult.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                _event.emit(LoginEvent.Error(result.message ?: "Login failed"))
            }
            is NetworkResult.Loading -> {
                _uiState.update { it.copy(isLoading = true) }
            }
        }
    }
    }

    private fun validateCredentials(request: LoginRequest): String? {
        if (request.email.isBlank() || request.password.isBlank()) {
            return "Email and password cannot be empty"
        }
        if (!EMAIL_REGEX.matches(request.email)) {
            return "Invalid email format"
        }
        return null
    }

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }
}

data class LoginUiState(
    val loginRequest: LoginRequest? = null,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
)

sealed interface LoginEvent {
    data class Success(val data: AuthResponse?) : LoginEvent
    data class Error(val message: String) : LoginEvent
}
