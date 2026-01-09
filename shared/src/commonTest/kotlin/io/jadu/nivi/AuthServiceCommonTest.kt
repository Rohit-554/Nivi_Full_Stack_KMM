package io.jadu.nivi

import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.service.AuthService
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Common multiplatform test for AuthService
 * Works on Android, iOS, and JVM by using platform-specific HTTP client engines
 */
class AuthServiceCommonTest {

    private val testClient = createTestHttpClient()
    private val authService = AuthService(client = testClient)

    @Test
    fun testRegisterNewUserReturnsSuccess() = runBlocking {
        val randomId = Random.nextInt(0, 10000)
        val request = RegisterRequest(
            name = "Test User $randomId",
            email = "test$randomId@example.com",
            password = "password123"
        )

        val result = authService.register(request)

        assertTrue(result is NetworkResult.Success, "Expected Success but got $result")
        val data = result.data
        println("✅ TEST PASSED: Created user ${data.userName} with Token: ${data.token.take(10)}...")
    }

    @Test
    fun testLoginWithWrongPasswordReturnsError() = runBlocking {
        val request = LoginRequest(
            email = "test@example.com",
            password = "WRONG_PASSWORD_123"
        )

        val result = authService.login(request)

        assertTrue(result is NetworkResult.Error, "Expected Error but got $result")
        val message = result.message
        println("✅ TEST PASSED: Backend correctly rejected login. Message: $message")
    }

    @Test
    fun testRegisterWithDuplicateEmailReturnsError() = runBlocking {
        // First, register a user
        val randomId = Random.nextInt(10000, 20000)
        val email = "duplicate$randomId@example.com"

        val firstRequest = RegisterRequest(
            name = "First User",
            email = email,
            password = "password123"
        )

        val firstResult = authService.register(firstRequest)
        assertTrue(firstResult is NetworkResult.Success, "First registration should succeed")

        // Try to register again with same email
        val secondRequest = RegisterRequest(
            name = "Second User",
            email = email,
            password = "password456"
        )

        val secondResult = authService.register(secondRequest)
        assertTrue(secondResult is NetworkResult.Error, "Duplicate registration should fail")
        println("✅ TEST PASSED: Duplicate email correctly rejected. Message: ${secondResult.message}")
    }
}

