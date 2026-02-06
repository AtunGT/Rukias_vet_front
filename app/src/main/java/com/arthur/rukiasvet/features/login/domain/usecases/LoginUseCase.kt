package com.arthur.rukiasvet.features.login.domain.usecases

import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.features.login.domain.entities.UsuarioSesion

class LoginUseCase(
    private val repository: VeterinaryRepository
) {
    suspend operator fun invoke(usuario: String, contrasena: String): UsuarioSesion {
        return repository.iniciarSesion(usuario, contrasena)
    }
}