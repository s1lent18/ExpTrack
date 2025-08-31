package com.example.moneytracker.models.interfaces

import com.example.moneytracker.models.model.AddCommuteRequest
import com.example.moneytracker.models.model.AddCommuteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommuteAPI {

    @POST("/user/{userId}/addCommute")
    suspend fun addCommute(
        @Path("userId") userId: Int,
        @Body request: AddCommuteRequest,
        @Header("Authorization") token: String,
    ): Response<AddCommuteResponse>

    @DELETE("user/{userId}/deleteCommute")
    suspend fun deleteCommute(
        @Path("userId") userId: Int,
        @Query("id") commuteId: Int
    ): Response<String>

    @GET("/user/{userId}/getCommutes")
    suspend fun getCommutes(
        @Path("userId") userId: Int
    ) : List<AddCommuteResponse>

}