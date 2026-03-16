package com.arthur.rukiasvet.features.login.domain.repositories

import com.arthur.rukiasvet.features.login.domain.entities.UserSession

interface VeterinaryRepository {
    suspend fun login(email: String, password: String): UserSession
    suspend fun registerUser(name: String, lastname: String, email: String, password: String): Boolean
}