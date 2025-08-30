package com.example.moneytracker.models.model

data class SignUpResponse(
    val budget: Int,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val total: Int,
    val token: String
)
