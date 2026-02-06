package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PatientViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientUIState())
    val uiState: StateFlow<PatientUIState> = _uiState.asStateFlow()

    fun onNombreChange(v: String) { _uiState.update { it.copy(nombre = v) } }
    fun onPesoChange(v: String) { _uiState.update { it.copy(peso = v) } }
    fun onEdadChange(v: String) { _uiState.update { it.copy(edad = v) } }
    fun onDuenoChange(v: String) { _uiState.update { it.copy(dueno = v) } }
    fun onTelefonoChange(v: String) { _uiState.update { it.copy(telefono = v) } }
    fun onDescripcionChange(v: String) { _uiState.update { it.copy(descripcion = v) } }

    fun limpiarFormulario() {
        _uiState.update { it.copy(nombre = "", peso = "", edad = "", dueno = "", telefono = "", descripcion = "", mensajeExito = false, mensajeError = "") }
    }


    fun cargarPacientes(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val lista = repository.getPatients(token)
            _uiState.update { it.copy(isLoading = false, listaPacientes = lista) }
        }
    }

    fun guardarPaciente(token: String, onSuccess: () -> Unit) {
        val currentState = _uiState.value
        if (currentState.nombre.isEmpty() || currentState.peso.isEmpty() ||
            currentState.dueno.isEmpty() || currentState.telefono.isEmpty()) {
            _uiState.update { it.copy(mensajeError = "Llena los campos obligatorios (*)") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }

            val paciente = PatientRequest(
                name = currentState.nombre,
                species = "Perro",
                description = currentState.descripcion,
                gender = "M",
                weight = currentState.peso.toDoubleOrNull() ?: 0.0,
                age = "${currentState.edad} años",
                owner = currentState.dueno,
                telephone = currentState.telefono
            )

            val exito = repository.addPatient(token, paciente)

            if (exito) {
                _uiState.update { it.copy(mensajeExito = true, isLoading = false) }
                limpiarFormulario()
                cargarPacientes(token)
                onSuccess()
            } else {
                _uiState.update { it.copy(mensajeError = "Error al guardar", isLoading = false) }
            }
        }
    }
}