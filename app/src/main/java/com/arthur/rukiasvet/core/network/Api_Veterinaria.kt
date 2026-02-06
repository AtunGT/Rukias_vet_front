package com.arthur.rukiasvet.core.network


import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientResponse
import com.arthur.rukiasvet.login.rukiasvet.data.model.LoginRequest
import com.arthur.rukiasvet.login.rukiasvet.data.model.LoginResponse
import com.arthur.rukiasvet.login.rukiasvet.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api_Veterinaria {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registrarUsuario(@Body request: RegisterRequest): Response<Void>

    @POST("patients")
    suspend fun agregarPaciente(
        @Header("Authorization") token: String,
        @Body paciente: PatientRequest
    ): Response<Void>

    @GET("patients")
    suspend fun obtenerPacientes(
        @Header("Authorization") token: String
    ): Response<List<PatientResponse>>
}