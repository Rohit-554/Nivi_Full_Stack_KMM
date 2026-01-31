package io.jadu.nivi.data.repository

import io.jadu.nivi.domain.repository.AuthRepository
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.service.AuthService

class AuthRepositoryImpl (private val authService: AuthService) : AuthRepository {
    override suspend fun login(request: LoginRequest): NetworkResult<AuthResponse> {
        return authService.login(request)
    }

    override suspend fun signUp(request: RegisterRequest): NetworkResult<AuthResponse> {
        return authService.register(request)
    }
}