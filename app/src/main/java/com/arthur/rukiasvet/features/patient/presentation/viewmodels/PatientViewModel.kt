package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.core.session.SessionRepository
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.usecases.AddPatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.DeletePatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetAllPatientsUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetPatientByIdUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetPatientsByBranchUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.UpdatePatientUseCase
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val addPatientUseCase: AddPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val getPatientsByBranchUseCase: GetPatientsByBranchUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase,
    private val getPatientByIdUseCase: GetPatientByIdUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientUIState())
    val uiState: StateFlow<PatientUIState> = _uiState.asStateFlow()

    private val _capturedImageFile = MutableStateFlow<File?>(null)
    val capturedImageFile: StateFlow<File?> = _capturedImageFile.asStateFlow()

    var editingPatientId: Int? = null
        private set

    fun onNameChange(value: String) { _uiState.update { it.copy(nombre = value) } }
    fun onWeightChange(value: String) { _uiState.update { it.copy(peso = value) } }
    fun onAgeChange(value: String) { _uiState.update { it.copy(edad = value) } }
    fun onOwnerChange(value: String) { _uiState.update { it.copy(dueno = value) } }
    fun onPhoneChange(value: String) { _uiState.update { it.copy(telefono = value) } }
    fun onDescriptionChange(value: String) { _uiState.update { it.copy(descripcion = value) } }


    fun onGenderChange(value: String) { _uiState.update { it.copy(genero = value) } }
    fun onSpeciesChange(value: String) { _uiState.update { it.copy(especie = value) } }

    fun setCapturedImage(file: File) {
        _capturedImageFile.value = file
    }

    fun clearImage() {
        _capturedImageFile.value = null
    }

    fun loadPatients() {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: run {
                _uiState.update { it.copy(mensajeError = "Sesión no válida") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }
            val list = getAllPatientsUseCase(token)
            _uiState.update { it.copy(isLoading = false, listaPacientes = list) }
        }
    }

    fun loadPatientsByBranch(branchId: Int) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: run {
                _uiState.update { it.copy(mensajeError = "Sesión no válida") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }
            val list = getPatientsByBranchUseCase(token, branchId)
            _uiState.update { it.copy(isLoading = false, listaPacientes = list) }
        }
    }

    fun loadPatientForEdit(id: Int) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: return@launch
            val patient = getPatientByIdUseCase(token, id)
            patient?.let { startEdit(it) }
                ?: _uiState.update { it.copy(mensajeError = "No se pudo cargar el paciente") }
        }
    }

    fun startEdit(patient: Patient) {
        editingPatientId = patient.id
        _uiState.update {
            it.copy(
                nombre = patient.name,
                peso = patient.weight.toString(),
                edad = patient.age,
                dueno = patient.owner,
                telefono = patient.telephone,
                descripcion = patient.description,
                genero = patient.gender,
                especie = patient.species
            )
        }
    }

    fun deletePatient(patient: Patient, branchId: Int? = null) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: return@launch
            _uiState.update { it.copy(isLoading = true) }
            val success = deletePatientUseCase(token, patient.id)
            if (success) {
                if (branchId != null) loadPatientsByBranch(branchId)
                else loadPatients()
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al eliminar") }
            }
        }
    }

    fun savePatient(branchId: Int, userId: Int, onSuccess: () -> Unit) {
        val s = _uiState.value
        if (!validateFields(s)) return

        val request = PatientRequest(
            branchId = branchId,
            userId = userId,
            name = s.nombre,
            species = s.especie,
            description = s.descripcion,
            gender = s.genero,
            weight = s.peso.toDoubleOrNull() ?: 0.0,
            age = s.edad,
            owner = s.dueno,
            telephone = s.telefono,
            imageUrl = ""
        )

        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: run {
                _uiState.update { it.copy(mensajeError = "Sesión no válida") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }

            val imageFile = _capturedImageFile.value
            val success = if (editingPatientId == null) {
                addPatientUseCase(token, request, imageFile)
            } else {
                updatePatientUseCase(token, editingPatientId!!, request, imageFile)
            }

            if (success) {
                clearForm()
                onSuccess()
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al guardar") }
            }
        }
    }

    private fun validateFields(s: PatientUIState): Boolean {
        val valid = s.nombre.isNotBlank() && s.peso.isNotBlank() &&
                s.edad.isNotBlank() && s.dueno.isNotBlank() &&
                s.telefono.isNotBlank() && s.genero.isNotBlank() && s.especie.isNotBlank()
        if (!valid) _uiState.update { it.copy(mensajeError = "Todos los campos son obligatorios") }
        return valid
    }

    fun clearForm() {
        editingPatientId = null
        clearImage()
        _uiState.update { PatientUIState() }
    }

    fun clearMessages() {
        _uiState.update { it.copy(mensajeError = "", mensajeExito = false) }
    }
}