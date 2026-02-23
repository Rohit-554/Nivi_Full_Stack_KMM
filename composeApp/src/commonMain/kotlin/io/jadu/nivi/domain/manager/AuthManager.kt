package io.jadu.nivi.domain.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * AuthManager handles authentication state persistence
 * Stores and retrieves auth tokens and user data
 */
class AuthManager(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    /**
     * Save authentication data after successful login/signup
     */
    suspend fun saveAuthData(token: String, userName: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
            preferences[USER_NAME_KEY] = userName
        }
    }

    /**
     * Get the stored auth token
     */
    suspend fun getAuthToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }.first()
    }

    /**
     * Get the stored user name
     */
    suspend fun getUserName(): String? {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }.first()
    }

    /**
     * Check if user is logged in (has valid token)
     */
    suspend fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    /**
     * Clear all authentication data (logout)
     */
    suspend fun clearAuthData() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
            preferences.remove(USER_NAME_KEY)
        }
    }
}

