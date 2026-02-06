package com.arthur.rukiasvet.features.login.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VeterinaryViewModel(
    private val repository: VeterinaryRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(VeterinaryUIState())
    val uiState: StateFlow<VeterinaryUIState> = _uiState.asStateFlow()


    var usuario by mutableStateOf("")
    var contrasena by mutableStateOf("")

    var regNombre by mutableStateOf("")
    var regApellidos by mutableStateOf("")
    var regEmail by mutableStateOf("")
    var regPassword by mutableStateOf("")
    var regConfirmPassword by mutableStateOf("")

    fun cambiarModo(registro: Boolean) {
        _uiState.update { it.copy(esRegistro = registro, mensajeError = "", mensajeExito = "") }
    }

    fun iniciarSesion() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }
            val sesion = repository.iniciarSesion(usuario, contrasena)

            if (sesion.esValido) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        nombreUsuario = "Usuario",
                        diagnosticoReal = sesion.tokenRaw
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Credenciales incorrectas") }
            }
        }
    }

    fun registrar() {
        if (regPassword != regConfirmPassword) {
            _uiState.update { it.copy(mensajeError = "Las contraseñas no coinciden") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }
            val exito = repository.registrarUsuario(regNombre, regApellidos, regEmail, regPassword)

            if (exito) {
                _uiState.update { it.copy(isLoading = false, mensajeExito = "Registro exitoso. Inicia sesión.") }

            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al registrar") }
            }
        }
    }

    fun cerrarSesion() {
        _uiState.update { VeterinaryUIState() }
        usuario = ""; contrasena = ""
    }
}