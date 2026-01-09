package io.jadu.nivi

import io.ktor.client.HttpClient

/**
 * Expect function to provide platform-specific HttpClient for testing
 * Each platform will provide its own implementation with the appropriate engine
 */
expect fun createTestHttpClient(): HttpClient

/**
 * Expect function to provide platform-specific authenticated HttpClient for testing
 * with JWT Bearer token
 */
expect fun createAuthenticatedTestHttpClient(token: String): HttpClient
