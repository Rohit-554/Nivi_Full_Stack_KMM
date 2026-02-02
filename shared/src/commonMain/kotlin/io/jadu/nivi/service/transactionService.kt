package io.jadu.nivi.service

import io.jadu.nivi.data.remote.KtorClient
import io.jadu.nivi.models.ApiResponse
import io.jadu.nivi.models.NetworkResult
import io.jadu.nivi.models.Transaction
import io.jadu.nivi.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class TransactionService(
    private val client: HttpClient = KtorClient.httpClient
) {
    suspend fun getTransactions() : NetworkResult<List<Transaction>> {
        return safeApiCall {
            client.get("/transactions").body<ApiResponse<List<Transaction>>>()
        }
    }

    suspend fun createTransaction(request: Transaction) : NetworkResult<Transaction> {
        return safeApiCall {
            client.post("/transactions") {
                setBody(request)
            }.body<ApiResponse<Transaction>>()
        }
    }
}