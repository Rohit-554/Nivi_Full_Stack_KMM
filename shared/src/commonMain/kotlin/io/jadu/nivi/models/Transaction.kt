package io.jadu.nivi.models

import kotlinx.serialization.Serializable

enum class TransactionType { INCOME, EXPENSE }
enum class Necessity { NEED, WANT, INVESTMENT }

@Serializable
data class Transaction (
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val necessity: Necessity,
    val category: String,
    val date: String
)