package com.example.moneytracker.models

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.moneytracker.models.model.SignUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPrefImpl (private val dataStore: DataStore<Preferences>) : UserPref {

    override fun getUserData(): Flow<SignUpResponse> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }.map { preferences ->
                preferences[USER_KEY]?.let {
                    try {
                        Json.decodeFromString<SignUpResponse>(it)
                    } catch (_: Exception) {
                        null
                    }
                } as SignUpResponse
            }
    }

    override fun getTimeStamp(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[TIMESTAMP_KEY]?: ""
        }
    }

    override suspend fun saveUserData(signUpResponse: SignUpResponse) {
        val jsonString = Json.encodeToString(signUpResponse)
        dataStore.edit { preferences ->
            preferences[USER_KEY] = jsonString
        }
    }

    override suspend fun saveTimeStamp(timestamp: String) {
        dataStore.edit {
            it[TIMESTAMP_KEY] = timestamp
        }
    }
}