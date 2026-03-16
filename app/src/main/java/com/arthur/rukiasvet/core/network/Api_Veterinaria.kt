package com.arthur.rukiasvet.core.network

import com.arthur.rukiasvet.features.login.data.model.LoginRequest
import com.arthur.rukiasvet.features.login.data.model.LoginResponse
import com.arthur.rukiasvet.features.login.data.model.RegisterRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import retrofit2.Response
import retrofit2.http.*

interface Api_Veterinaria {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("users")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<Void>

    @POST("patients")
    suspend fun addPatient(
        @Header("Authorization") token: String,
        @Body patient: PatientRequest
    ): Response<Void>

    @GET("patients")
    suspend fun getPatients(
        @Header("Authorization") token: String
    ): Response<List<PatientRequest>>

    @DELETE("patients/{id}")
    suspend fun deletePatient(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>

    @PUT("patients/{id}")
    suspend fun updatePatient(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body patient: PatientRequest
    ): Response<Void>
}