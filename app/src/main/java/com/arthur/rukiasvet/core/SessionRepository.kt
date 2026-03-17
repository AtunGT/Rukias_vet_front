package com.arthur.rukiasvet.core.session

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface SessionRepository {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun clearToken()
    suspend fun getCurrentUserId(): Int?
    suspend fun saveUserId(userId: Int)
}

interface SessionManager {
    val token: StateFlow<String?>
    val userId: StateFlow<Int?>
    val isAuthenticated: StateFlow<Boolean>

    suspend fun login(token: String, userId: Int)
    suspend fun logout()
}


@Singleton
class SessionManagerImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : SessionManager {

    private val _token = MutableStateFlow<String?>(null)
    override val token = _token.asStateFlow()

    private val _userId = MutableStateFlow<Int?>(null)
    override val userId = _userId.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    override val isAuthenticated = _isAuthenticated.asStateFlow()

    // ← Carga el token guardado al iniciar
    init {
        kotlinx.coroutines.GlobalScope.launch {
            val savedToken = sessionRepository.getToken()
            val savedUserId = sessionRepository.getCurrentUserId()
            if (savedToken != null) {
                _token.update { savedToken }
                _userId.update { savedUserId }
                _isAuthenticated.update { true }
            }
        }
    }

    override suspend fun login(token: String, userId: Int) {
        sessionRepository.saveToken(token)
        sessionRepository.saveUserId(userId)
        _token.update { token }
        _userId.update { userId }
        _isAuthenticated.update { true }
    }

    override suspend fun logout() {
        sessionRepository.clearToken()
        _token.update { null }
        _userId.update { null }
        _isAuthenticated.update { false }
    }
}