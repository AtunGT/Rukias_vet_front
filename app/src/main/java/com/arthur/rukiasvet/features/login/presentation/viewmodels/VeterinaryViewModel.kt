package com.arthur.rukiasvet.features.login.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class VeterinaryViewModel @Inject constructor(
    private val repository: VeterinaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeterinaryUIState())
    val uiState: StateFlow<VeterinaryUIState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var regName by mutableStateOf("")
    var regLastname by mutableStateOf("")
    var regEmail by mutableStateOf("")
    var regPassword by mutableStateOf("")
    var regConfirmPassword by mutableStateOf("")

    fun switchMode(isRegister: Boolean) {
        _uiState.update { it.copy(esRegistro = isRegister, mensajeError = "", mensajeExito = "") }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }

            val session = repository.login(email, password)

            if (session.isValid) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        nombreUsuario = "Usuario",
                        diagnosticoReal = session.tokenRaw,
                        decodedData = session.decodedData
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "Credenciales incorrectas"
                    )
                }
            }
        }
    }

    fun register() {
        if (regPassword != regConfirmPassword) {
            _uiState.update { it.copy(mensajeError = "Las contraseñas no coinciden") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }

            val success = repository.registerUser(
                regName,
                regLastname,
                regEmail,
                regPassword
            )

            if (success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeExito = "Registro exitoso. Inicia sesión."
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "Error al registrar"
                    )
                }
            }
        }
    }

    fun logout() {
        _uiState.update { VeterinaryUIState() }
        email = ""
        password = ""
    }
}