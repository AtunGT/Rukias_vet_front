package com.arthur.rukiasvet.features.login.data.datasources.remote.mapper

import com.arthur.rukiasvet.core.utils.Tokendeco
import com.arthur.rukiasvet.features.login.domain.entities.UsuarioSesion

fun mapTokenToSession(tokenRaw: String?, decoder: Tokendeco): UsuarioSesion {
    if (tokenRaw.isNullOrEmpty()) {
        return UsuarioSesion(
            tokenRaw = "",
            datosDecodificados = emptyMap(),
            esValido = false
        )
    }

    val tokenLimpio = tokenRaw.replace("Bearer ", "", ignoreCase = true).trim()

    return try {
        val datos = decoder.decodificarPayload(tokenLimpio)
        UsuarioSesion(
            tokenRaw = tokenLimpio,
            datosDecodificados = datos,
            esValido = true
        )
    } catch (e: Exception) {
        UsuarioSesion("", emptyMap(), false)
    }
}