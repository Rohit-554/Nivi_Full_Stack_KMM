package io.jadu.nivi.domain.repository

import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest) : NetworkResult<AuthResponse>
    suspend fun signUp(request: RegisterRequest) : NetworkResult<AuthResponse>
}