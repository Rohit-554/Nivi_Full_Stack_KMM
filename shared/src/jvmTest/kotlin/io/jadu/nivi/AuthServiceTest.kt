package io.jadu.nivi

import io.jadu.nivi.models.LoginRequest
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.RegisterRequest
import io.jadu.nivi.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthServiceTest {

    private val testClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { 
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("http://localhost:8080")
            contentType(ContentType.Application.Json)
        }
    }

    private val authService = AuthService(client = testClient)

    @Test
    fun `test register new user returns success`() = runBlocking {
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
    fun `test login with wrong password returns error`() = runBlocking {
        val request = LoginRequest(
            email = "test@example.com",
            password = "WRONG_PASSWORD_123"
        )

        val result = authService.login(request)

        assertTrue(result is NetworkResult.Error, "Expected Error but got $result")
        val message = (result as NetworkResult.Error).message
        println("✅ TEST PASSED: Backend correctly rejected login. Message: $message")
    }
}