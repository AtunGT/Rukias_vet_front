package com.arthur.rukiasvet.features.login.domain.usecases

import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository

class RegisterUseCase(private val repository: VeterinaryRepository) {

    suspend operator fun invoke(name: String, lastname: String, email: String, password: String): Boolean {
        return repository.registerUser(name, lastname, email, password)
    }
}