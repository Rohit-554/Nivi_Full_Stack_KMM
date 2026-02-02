package io.jadu.nivi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.nivi.domain.useCase.LoginUseCase
import io.jadu.nivi.domain.useCase.SignupUseCase
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.presentation.viewModel.AuthUiState.Error
import io.jadu.nivi.presentation.viewModel.AuthUiState.Loading
import io.jadu.nivi.presentation.viewModel.AuthUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel (
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState : MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.value = Loading

        viewModelScope.launch {
            // FIX: Sanitize input to remove accidental tabs/spaces
            val cleanEmail = email.trim().lowercase()

            val request = LoginRequest(cleanEmail, password)

            when (val result = loginUseCase(request)) {
                is NetworkResult.Success -> {
                    _uiState.value = Success(result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = Error(result.message)
                }
                NetworkResult.Loading -> {
                    _uiState.value = Loading
                }
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        _uiState.value = Loading

        viewModelScope.launch {
            // FIX: Sanitize input here as well
            val cleanName = name.trim()
            val cleanEmail = email.trim().lowercase()

            val request = RegisterRequest(cleanName, cleanEmail, password)

            when (val result = registerUseCase(request)) {
                is NetworkResult.Success -> {
                    _uiState.value = Success(result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = Error(result.message)
                }
                NetworkResult.Loading -> {
                    _uiState.value = Loading
                }
            }
        }
    }
}

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val data: Any?) : AuthUiState() // Replace Any? with your AuthResponse
    data class Error(val message: String?) : AuthUiState()
}