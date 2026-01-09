package io.jadu.nivi.service

import io.jadu.nivi.data.remote.KtorClient
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthService (
    private val client: HttpClient = KtorClient.httpClient
) {
    suspend fun login(request: LoginRequest): NetworkResult<AuthResponse> {
        return safeApiCall {
            // "post" uses the Base URL defined in KtorClient automatically
            client.post("/auth/login") {
                setBody(request)
            }.body<ApiResponse<AuthResponse>>()
        }
    }

    suspend fun register(request: RegisterRequest): NetworkResult<AuthResponse> {
        return safeApiCall {
            client.post("/auth/register") {
                setBody(request)
            }.body<ApiResponse<AuthResponse>>()
        }
    }
}