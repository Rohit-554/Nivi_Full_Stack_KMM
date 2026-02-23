package io.jadu.nivi.domain.useCase

import io.jadu.nivi.domain.manager.AuthManager

/**
 * Use case for logging out the user
 * Clears all stored authentication data
 */
class LogoutUseCase(
    private val authManager: AuthManager
) {
    suspend operator fun invoke() {
        authManager.clearAuthData()
    }
}

