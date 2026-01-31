package io.jadu.nivi.domain.useCase

import io.jadu.nivi.domain.repository.AuthRepository
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult

class LoginUseCase (private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) : NetworkResult<AuthResponse> {
        return repository.login(request)
    }
}