package com.arthur.rukiasvet.features.login.domain.usecases

import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.features.login.domain.entities.UserSession

class LoginUseCase(
    private val repository: VeterinaryRepository
) {
    suspend operator fun invoke(email: String, password: String): UserSession {
        return repository.login(email, password)
    }
}