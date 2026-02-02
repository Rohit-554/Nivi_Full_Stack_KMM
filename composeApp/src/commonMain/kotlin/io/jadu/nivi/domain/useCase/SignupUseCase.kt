package io.jadu.nivi.domain.useCase

import io.jadu.nivi.domain.repository.AuthRepository
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest

class SignupUseCase (private val repository: AuthRepository)  {
    suspend operator fun invoke(request: RegisterRequest) : NetworkResult<AuthResponse> {
        return repository.signUp(request)
    }
}