package com.example.moneytracker.models.model

data class SignUpResponse(
    val budget: Int =  0,
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val password: String = "",
    val total: Int = 0,
    val token: String = ""
)
