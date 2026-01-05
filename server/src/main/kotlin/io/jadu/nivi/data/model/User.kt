package io.jadu.nivi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val email: String,
    val name: String,
    val passwordHash: String
)