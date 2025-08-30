package com.example.moneytracker.viewmodels.navigation

import androidx.lifecycle.ViewModel
import com.example.moneytracker.models.NetworkResponse
import com.example.moneytracker.models.interfaces.CredentialsAPI
import com.example.moneytracker.models.model.SignUpResponse
import com.example.moneytracker.models.model.ValidateResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val credentialsAPI: CredentialsAPI
) : ViewModel() {

    private val _loginResult = MutableStateFlow<NetworkResponse<SignUpResponse>?>(null)
    val loginResult: StateFlow<NetworkResponse<SignUpResponse>?> = _loginResult

    private val _signUpResult = MutableStateFlow<NetworkResponse<SignUpResponse>?>(null)
    val signUpResult : StateFlow<NetworkResponse<SignUpResponse>?> = _signUpResult

    private val _validateResult = MutableStateFlow<NetworkResponse<ValidateResponse>?>(null)
    val validateResult : StateFlow<NetworkResponse<ValidateResponse>?> = _validateResult
}