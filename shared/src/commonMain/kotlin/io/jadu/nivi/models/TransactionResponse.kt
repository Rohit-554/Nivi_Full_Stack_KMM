package io.jadu.nivi.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val transactions: List<Transaction>,
    val status: String,
    val healthScore: Int,
    val averagePerTransaction: Double,
    val volatility: Double,
)
