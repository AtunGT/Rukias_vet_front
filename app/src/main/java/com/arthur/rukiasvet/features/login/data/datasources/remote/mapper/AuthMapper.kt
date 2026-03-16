package com.arthur.rukiasvet.features.login.data.datasources.remote.mapper

import com.arthur.rukiasvet.core.utils.TokenDeco
import com.arthur.rukiasvet.features.login.domain.entities.UserSession


fun mapTokenToSession(rawToken: String?, decoder: TokenDeco): UserSession {
    if (rawToken.isNullOrEmpty()) {
        return UserSession(
            tokenRaw = "",
            decodedData = emptyMap(),
            isValid = false
        )
    }

    val cleanToken = rawToken.replace("Bearer ", "", ignoreCase = true).trim()

    return try {
        val data = decoder.decodePayload(cleanToken)
        UserSession(
            tokenRaw = cleanToken,
            decodedData = data,
            isValid = true
        )
    } catch (e: Exception) {
        UserSession("", emptyMap(), false)
    }
}