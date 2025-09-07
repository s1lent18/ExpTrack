package com.example.moneytracker.models

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moneytracker.models.model.LoginResponse
import com.example.moneytracker.models.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

val USER_KEY : Preferences.Key<String> = stringPreferencesKey("userData")
val TIMESTAMP_KEY = stringPreferencesKey("timestamp")


interface UserPref {

    fun getUserData() : Flow<LoginResponse>
    fun getTimeStamp(): Flow<String>

    suspend fun saveUserData(loginResponse: LoginResponse)
    suspend fun saveTimeStamp(timestamp: String)
}