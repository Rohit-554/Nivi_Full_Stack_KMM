package io.jadu.nivi.data.remote

import io.jadu.nivi.utils.Constants
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {

    private val jsonConfig = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val httpClient = HttpClient {
        // json translator
        install(ContentNegotiation) {
            json(jsonConfig)
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }

        defaultRequest {
            url(Constants.BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }

}