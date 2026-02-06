package com.arthur.rukiasvet.features.login.domain.usecases

import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository

class RegisterUseCase(private val repository: VeterinaryRepository) {

    suspend operator fun invoke(nombre: String, apellidos: String, email: String, pass: String): Boolean {
        return repository.registrarUsuario(nombre, apellidos, email, pass)
    }
}