package com.arthur.rukiasvet.core.network

import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.data.model.BranchResponse
import com.arthur.rukiasvet.features.login.data.model.LoginRequest
import com.arthur.rukiasvet.features.login.data.model.LoginResponse
import com.arthur.rukiasvet.features.login.data.model.RegisterRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import okhttp3.RequestBody

interface Api_Veterinaria {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("users")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<Void>

    @GET("patients")
    suspend fun getPatients(
        @Header("Authorization") token: String
    ): Response<List<PatientResponse>>

    @GET("patients")
    suspend fun getPatientsByBranch(
        @Header("Authorization") token: String,
        @Query("branch_id") branchId: Int
    ): Response<List<PatientResponse>>

    @GET("patients/{id}")
    suspend fun getPatientById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<PatientResponse>

    @DELETE("patients/{id}")
    suspend fun deletePatient(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

    @Multipart
    @POST("patients")
    suspend fun addPatient(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("description") description: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("age") age: RequestBody,
        @Part("owner") owner: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("branch_id") branchId: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PatientRequest>

    @Multipart
    @PUT("patients/{id}")
    suspend fun updatePatient(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("description") description: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("age") age: RequestBody,
        @Part("owner") owner: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("branch_id") branchId: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PatientRequest>

    @POST("branches")
    suspend fun addBranch(
        @Header("Authorization") token: String,
        @Body branch: BranchRequest
    ): Response<BranchResponse>

    @GET("branches")
    suspend fun getBranches(
        @Header("Authorization") token: String
    ): Response<List<BranchResponse>>

    @DELETE("branches/{id}")
    suspend fun deleteBranch(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

    @PUT("branches/{id}")
    suspend fun updateBranch(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body branch: BranchRequest
    ): Response<BranchResponse>
}
