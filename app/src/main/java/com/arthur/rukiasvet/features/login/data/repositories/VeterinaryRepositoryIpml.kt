package com.arthur.rukiasvet.features.login.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.core.utils.TokenDeco
import com.arthur.rukiasvet.features.login.data.datasources.remote.mapper.mapTokenToSession
import com.arthur.rukiasvet.features.login.data.model.LoginRequest
import com.arthur.rukiasvet.features.login.data.model.RegisterRequest
import com.arthur.rukiasvet.features.login.domain.entities.UserSession
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import javax.inject.Inject

class VeterinaryRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria,
    private val tokenDeco: TokenDeco
) : VeterinaryRepository {

    override suspend fun login(email: String, password: String): UserSession {
        return try {
            val response = api.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val token =
                    response.headers()["Authorization"]
                        ?: response.headers()["authorization"]
                        ?: response.body()?.token

                mapTokenToSession(token, tokenDeco)
            } else {
                UserSession("", emptyMap(), false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UserSession("", emptyMap(), false)
        }
    }

    override suspend fun registerUser(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): Boolean {
        return try {
            val request = RegisterRequest(name, lastname, email, password)
            val response = api.registerUser(request)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}