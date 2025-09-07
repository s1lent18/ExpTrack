package com.example.moneytracker.models.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val userData: UserData = UserData()
)