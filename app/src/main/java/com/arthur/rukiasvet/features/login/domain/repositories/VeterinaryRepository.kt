package com.arthur.rukiasvet.features.login.domain.repositories

import com.arthur.rukiasvet.features.login.domain.entities.UsuarioSesion

interface VeterinaryRepository {
    suspend fun iniciarSesion(usuario: String, contrasena: String): UsuarioSesion
    suspend fun registrarUsuario(nombre: String, apellidos: String, email: String, pass: String): Boolean
}