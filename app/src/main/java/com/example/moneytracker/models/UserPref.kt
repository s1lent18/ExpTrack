package com.example.moneytracker.models

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moneytracker.models.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

val USER_KEY : Preferences.Key<String> = stringPreferencesKey("userData")

interface UserPref {

    fun getUserData() : Flow<SignUpResponse>

    suspend fun saveUserData(signUpResponse : SignUpResponse)
}