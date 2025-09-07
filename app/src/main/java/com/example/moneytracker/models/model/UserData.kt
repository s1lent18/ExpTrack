package com.example.moneytracker.models.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val budget: Int = 0,
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val token: String = "",
    val total: Int = 0
)