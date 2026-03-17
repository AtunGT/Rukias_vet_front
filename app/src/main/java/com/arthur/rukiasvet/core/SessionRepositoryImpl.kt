package com.arthur.rukiasvet.core.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor() : SessionRepository {

    private var token: String? = null
    private var userId: Int? = null

    override suspend fun getToken(): String? = token
    override suspend fun saveToken(token: String) { this.token = token }
    override suspend fun clearToken() { this.token = null }
    override suspend fun getCurrentUserId(): Int? = userId
    override suspend fun saveUserId(userId: Int) { this.userId = userId }
}