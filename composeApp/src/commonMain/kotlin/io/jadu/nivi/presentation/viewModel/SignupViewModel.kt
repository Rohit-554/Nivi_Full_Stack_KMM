package io.jadu.nivi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.nivi.domain.manager.AuthManager
import io.jadu.nivi.domain.useCase.SignupUseCase
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCase: SignupUseCase,
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<SignupUiState> = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    private val _event = MutableSharedFlow<SignupEvent>()
    val event: SharedFlow<SignupEvent> = _event.asSharedFlow()

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            val current = currentState.registerRequest ?: RegisterRequest("", "", "")
            val updatedRequest = current.copy(email = email)
            currentState.copy(
                registerRequest = updatedRequest,
                isEnabled = updatedRequest.email.isNotBlank() &&
                           updatedRequest.password.isNotBlank() &&
                           currentState.confirmPassword.isNotBlank()
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            val current = currentState.registerRequest ?: RegisterRequest("", "", "")
            val updatedRequest = current.copy(password = password)
            currentState.copy(
                registerRequest = updatedRequest,
                isEnabled = updatedRequest.email.isNotBlank() &&
                           updatedRequest.password.isNotBlank() &&
                           currentState.confirmPassword.isNotBlank()
            )
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { currentState ->
            val current = currentState.registerRequest ?: RegisterRequest("", "", "")
            currentState.copy(
                confirmPassword = confirmPassword,
                isEnabled = current.email.isNotBlank() &&
                           current.password.isNotBlank() &&
                           confirmPassword.isNotBlank()
            )
        }
    }

    fun updateName(name: String) {
        _uiState.update { currentState ->
            val current = currentState.registerRequest ?: RegisterRequest("", "", "")
            val updatedRequest = current.copy(name = name)
            currentState.copy(
                registerRequest = updatedRequest,
                isEnabled = updatedRequest.name.isNotBlank() &&
                           updatedRequest.email.isNotBlank() &&
                           updatedRequest.password.isNotBlank() &&
                           currentState.confirmPassword.isNotBlank()
            )
        }
    }

    fun signup() {
        val request = _uiState.value.registerRequest ?: RegisterRequest("", "", "")
        val confirmPassword = _uiState.value.confirmPassword

        val sanitizedRequest = request.copy(
            name = request.name.trim(),
            email = request.email.trim().lowercase(),
            password = request.password.trim()
        )

        val validationError = validateCredentials(sanitizedRequest, confirmPassword.trim())
        if (validationError != null) {
            viewModelScope.launch { _event.emit(SignupEvent.Error(validationError)) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = signupUseCase(sanitizedRequest)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    // Save auth data for persistent login
                    result.data?.let { authResponse ->
                        authManager.saveAuthData(authResponse.token, authResponse.userName)
                    }
                    _event.emit(SignupEvent.Success(result.data))
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.emit(SignupEvent.Error(result.message))
                }
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun validateCredentials(request: RegisterRequest, confirmPassword: String): String? {
        if (request.email.isBlank()) {
            return "Email cannot be empty"
        }
        if (!EMAIL_REGEX.matches(request.email)) {
            return "Invalid email format"
        }
        if (request.password.isBlank()) {
            return "Password cannot be empty"
        }
        if (request.password.length < 6) {
            return "Password must be at least 6 characters"
        }
        if (request.password != confirmPassword) {
            return "Passwords do not match"
        }
        return null
    }

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }
}

data class SignupUiState(
    val registerRequest: RegisterRequest? = null,
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
)

sealed interface SignupEvent {
    data class Success(val data: AuthResponse?) : SignupEvent
    data class Error(val message: String) : SignupEvent
}
