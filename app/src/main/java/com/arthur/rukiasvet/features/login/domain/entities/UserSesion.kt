package com.arthur.rukiasvet.features.login.domain.entities

data class UserSession(
    val tokenRaw: String,
    val decodedData: Map<String, Any>,
    val isValid: Boolean
)