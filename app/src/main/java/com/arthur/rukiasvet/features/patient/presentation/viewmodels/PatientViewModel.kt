package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.usecases.AddPatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.DeletePatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetAllPatientsUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.UpdatePatientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PatientViewModel(
    private val addPatientUseCase: AddPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientUIState())
    val uiState: StateFlow<PatientUIState> = _uiState.asStateFlow()

    var genero by mutableStateOf("")
    var especie by mutableStateOf("")
    var editingPatientId: Int? by mutableStateOf(null)

    fun onNombreChange(v: String) { _uiState.update { it.copy(nombre = v) } }
    fun onPesoChange(v: String) { _uiState.update { it.copy(peso = v) } }
    fun onEdadChange(v: String) { _uiState.update { it.copy(edad = v) } }
    fun onDuenoChange(v: String) { _uiState.update { it.copy(dueno = v) } }
    fun onTelefonoChange(v: String) { _uiState.update { it.copy(telefono = v) } }
    fun onDescripcionChange(v: String) { _uiState.update { it.copy(descripcion = v) } }
    fun onGeneroChange(v: String) { genero = v }
    fun onEspecieChange(v: String) { especie = v }

    fun cargarPacientes(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val lista = getAllPatientsUseCase(token)
            _uiState.update { it.copy(isLoading = false, listaPacientes = lista) }
        }
    }

    fun startEdit(paciente: Patient) {
        editingPatientId = paciente.id
        genero = paciente.gender
        especie = paciente.species
        _uiState.update {
            it.copy(
                nombre = paciente.name,
                peso = paciente.weight.toString(),
                edad = paciente.age,
                dueno = paciente.owner,
                telefono = paciente.telephone,
                descripcion = paciente.description
            )
        }
    }

    fun deletePatient(token: String, paciente: Patient) {
        viewModelScope.launch {
            deletePatientUseCase(token, paciente.id)
            cargarPacientes(token)
        }
    }

    fun guardarPaciente(token: String, onSuccess: () -> Unit) {
        val s = _uiState.value

        if (
            s.nombre.isEmpty() ||
            s.peso.isEmpty() ||
            s.edad.isEmpty() ||
            s.dueno.isEmpty() ||
            s.telefono.isEmpty() ||
            genero.isEmpty() ||
            especie.isEmpty()
        ) {
            _uiState.update { it.copy(mensajeError = "Llena todos los campos") }
            return
        }

        val request = PatientRequest(
            name = s.nombre,
            species = especie,
            description = s.descripcion,
            gender = genero,
            weight = s.peso.toDoubleOrNull() ?: 0.0,
            age = s.edad,
            owner = s.dueno,
            telephone = s.telefono
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val success = if (editingPatientId == null) {
                addPatientUseCase(token, request)
            } else {
                updatePatientUseCase(token, editingPatientId!!, request)
            }

            if (success) {
                limpiarFormulario()
                cargarPacientes(token)
                onSuccess()
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al guardar") }
            }
        }
    }

    fun limpiarFormulario() {
        editingPatientId = null
        genero = ""
        especie = ""
        _uiState.update {
            it.copy(
                nombre = "",
                peso = "",
                edad = "",
                dueno = "",
                telefono = "",
                descripcion = "",
                mensajeError = "",
                mensajeExito = false,
                isLoading = false
            )
        }
    }
}
