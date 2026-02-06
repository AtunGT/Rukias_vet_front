package com.arthur.rukiasvet.features.login.data.repositories

import android.util.Log
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.core.utils.Tokendeco
import com.arthur.rukiasvet.features.login.data.datasources.remote.mapper.mapTokenToSession
import com.arthur.rukiasvet.features.login.domain.entities.UsuarioSesion
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.login.rukiasvet.data.model.LoginRequest
import com.arthur.rukiasvet.login.rukiasvet.data.model.RegisterRequest

class VeterinaryRepositoryImpl(
    private val api: Api_Veterinaria,
    private val tokendeco: Tokendeco
) : VeterinaryRepository {

    override suspend fun iniciarSesion(usuario: String, contrasena: String): UsuarioSesion {
        return try {
            val response = api.login(LoginRequest(usuario, contrasena))

            if (response.isSuccessful) {

                val tokenEncontrado = response.headers()["Authorization"]
                    ?: response.headers()["authorization"]
                    ?: response.body()?.token

                mapTokenToSession(tokenEncontrado, tokendeco)

            } else {
                UsuarioSesion("", emptyMap(), false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UsuarioSesion("", emptyMap(), false)
        }
    }

    override suspend fun registrarUsuario(nombre: String, apellidos: String, email: String, pass: String): Boolean {
        return try {
            val request = RegisterRequest(nombre, apellidos, email, pass)
            val response = api.registrarUsuario(request)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}