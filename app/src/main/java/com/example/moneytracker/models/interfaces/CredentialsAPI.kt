package com.example.moneytracker.models.interfaces

import com.example.moneytracker.models.model.LoginResponse
import com.example.moneytracker.models.model.SignUpRequest
import com.example.moneytracker.models.model.SignUpResponse
import com.example.moneytracker.models.model.ValidateRequest
import com.example.moneytracker.models.model.ValidateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CredentialsAPI {

    @POST("/user/validate")
    suspend fun validate(
        @Body validateRequest: ValidateRequest
    ) : Response<ValidateResponse>

    @POST("/user/signUp")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ) : Response<SignUpResponse>

    @POST("/user/login")
    suspend fun login(
        @Body loginRequest: ValidateRequest
    ) : Response<LoginResponse>
}