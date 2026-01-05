package io.jadu.nivi.utils

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
){
    companion object {
        // this is genric for success
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success =  true, data = data)
        }

        fun <T> error(message: String): ApiResponse<T> {
            return ApiResponse(success = false, data = null, message = message)
        }
    }
}