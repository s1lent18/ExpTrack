package com.example.moneytracker.models.model

import java.util.Date

data class AddCommuteRequest(
    val price: Int,
    val route: String,
    val date: Date
)