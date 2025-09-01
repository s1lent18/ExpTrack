package com.example.moneytracker.models.model

import java.util.Date

data class Commute(
    val id: Int,
    val price: Int,
    val route: String,
    val date: Date
)