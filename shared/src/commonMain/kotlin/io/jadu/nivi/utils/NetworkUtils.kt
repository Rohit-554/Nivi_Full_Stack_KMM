package io.jadu.nivi.utils

import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.NetworkResult

suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): NetworkResult<T> {
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