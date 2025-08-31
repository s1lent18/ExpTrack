package com.example.moneytracker.viewmodels.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.models.NetworkResponse
import com.example.moneytracker.models.UserPref
import com.example.moneytracker.models.interfaces.CredentialsAPI
import com.example.moneytracker.models.model.SignUpRequest
import com.example.moneytracker.models.model.SignUpResponse
import com.example.moneytracker.models.model.ValidateRequest
import com.example.moneytracker.models.model.ValidateResponse
import com.example.moneytracker.view.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val credentialsAPI: CredentialsAPI,
    private val userPref: UserPref
) : ViewModel() {

    init {
        checkSession()
        startAutoLogoutTimer()
    }

    private val _loginResult = MutableStateFlow<NetworkResponse<SignUpResponse>?>(null)
    val loginResult: StateFlow<NetworkResponse<SignUpResponse>?> = _loginResult

    private val _signUpResult = MutableStateFlow<NetworkResponse<SignUpResponse>?>(null)
    val signUpResult : StateFlow<NetworkResponse<SignUpResponse>?> = _signUpResult

    private val _validateResult = MutableStateFlow<NetworkResponse<ValidateResponse>?>(null)
    val validateResult : StateFlow<NetworkResponse<ValidateResponse>?> = _validateResult

    private val sessionDurationMillis = TimeUnit.MINUTES.toMillis(60)

    private val _session = MutableStateFlow(false)
    val session: StateFlow<Boolean> = _session

    val userData = userPref.getUserData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SignUpResponse()
    )

    val timeStamp = userPref.getTimeStamp().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    private fun checkSession() {
        viewModelScope.launch {
            val expired = isSessionExpired()
            _session.value = !expired
            if (expired) logout()
        }
    }

    private suspend fun isSessionExpired(): Boolean {
        val loginTimestamp = userPref.getTimeStamp().first().toLongOrNull() ?: return true
        return (System.currentTimeMillis() - loginTimestamp) > sessionDurationMillis
    }

    fun saveTimeStamp(timeStamp: String) {
        viewModelScope.launch {
            userPref.saveTimeStamp(timestamp = timeStamp)
        }
    }

    fun saveUserData(signUpResponse: SignUpResponse) {
        viewModelScope.launch {
            userPref.saveUserData(signUpResponse)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPref.saveUserData(SignUpResponse())
            userPref.saveTimeStamp("")
        }
    }

    fun saveData(signUpResponse: SignUpResponse, timeStamp: String) {
        saveUserData(signUpResponse)
        saveTimeStamp(timeStamp)
    }

    private fun startAutoLogoutTimer() {
        viewModelScope.launch {
            delay(sessionDurationMillis)
            logout()
        }
    }

    fun login(validateRequest: ValidateRequest) {

        Log.d("loginLogs", "$validateRequest")

        _loginResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = credentialsAPI.login(validateRequest)
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _loginResult.value = NetworkResponse.Success(it)
                        Log.d("loginLogs", "$response")
                    }
                }
                else {
                    _loginResult.value = NetworkResponse.Failure("Wrong Username / Password")
                    Log.d("loginLogs", "$response")
                }
            } catch (e: Exception) {
                _loginResult.value = NetworkResponse.Failure("$e")
                Log.d("loginLogs", "$e")
            }
        }
    }

    fun signUp(signUpRequest: SignUpRequest) {

        _signUpResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = credentialsAPI.signUp(signUpRequest = signUpRequest)
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _signUpResult.value = NetworkResponse.Success(it)
                    }
                }
                else {
                    _signUpResult.value = NetworkResponse.Failure("Wrong Username / Password")
                }
            } catch (e: Exception) {
                _signUpResult.value = NetworkResponse.Failure("$e")
            }
        }
    }

    fun validate(validateRequest: ValidateRequest) {

        _validateResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = credentialsAPI.validate(validateRequest)
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _validateResult.value = NetworkResponse.Success(it)
                    }
                }
                else {
                    _validateResult.value = NetworkResponse.Failure("Wrong Username / Password")
                }
            } catch (e: Exception) {
                _validateResult.value = NetworkResponse.Failure("$e")
            }
        }
    }
}