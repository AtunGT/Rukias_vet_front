package com.arthur.rukiasvet.features.login.domain.entities

data class UsuarioSesion(
    val tokenRaw: String,
    val datosDecodificados: Map<String, String>,
    val esValido: Boolean
)
