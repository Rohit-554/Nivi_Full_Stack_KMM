package io.jadu.nivi.service

import io.jadu.nivi.data.remote.KtorClient
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.AuthResponse
import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
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

    private suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): NetworkResult<T> {
        return try {
            val response = apiCall()

            if (response.success && response.data != null) {
                NetworkResult.Success(response.data)
            } else {
                NetworkResult.Error(response.message ?: "Unknown Server Error")
            }
        } catch (e: Exception) {
            // Handles No Internet, Timeout, Server Down (500), etc.
            NetworkResult.Error(e.message ?: "Network Request Failed")
        }
    }
}