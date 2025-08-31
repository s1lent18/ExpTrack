package com.example.moneytracker.models.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val budget: Int,
    val email: String,
    val name: String,
    val password: String,
    val total: Int
)