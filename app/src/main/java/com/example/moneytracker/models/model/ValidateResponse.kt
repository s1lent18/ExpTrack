package com.example.moneytracker.models.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateResponse(
    val message: String
)