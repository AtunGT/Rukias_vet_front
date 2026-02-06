package com.arthur.rukiasvet.login.rukiasvet.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("email") val usuario: String,
    @SerializedName("password") val contrasena: String
)

data class LoginResponse(

    @SerializedName("token") val token: String?
)

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)