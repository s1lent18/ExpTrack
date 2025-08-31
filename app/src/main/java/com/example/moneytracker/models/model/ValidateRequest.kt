package com.example.moneytracker.models.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateRequest(
    val email: String,
    val password: String
)