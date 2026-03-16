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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val addPatientUseCase: AddPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientUIState())
    val uiState: StateFlow<PatientUIState> = _uiState.asStateFlow()

    var gender by mutableStateOf("")
    var species by mutableStateOf("")
    var editingPatientId: Int? by mutableStateOf(null)

    fun onNameChange(v: String) { _uiState.update { it.copy(nombre = v) } }
    fun onWeightChange(v: String) { _uiState.update { it.copy(peso = v) } }
    fun onAgeChange(v: String) { _uiState.update { it.copy(edad = v) } }
    fun onOwnerChange(v: String) { _uiState.update { it.copy(dueno = v) } }
    fun onPhoneChange(v: String) { _uiState.update { it.copy(telefono = v) } }
    fun onDescriptionChange(v: String) { _uiState.update { it.copy(descripcion = v) } }
    fun onGenderChange(v: String) { gender = v }
    fun onSpeciesChange(v: String) { species = v }

    fun loadPatients(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val list = getAllPatientsUseCase(token)
            _uiState.update { it.copy(isLoading = false, listaPacientes = list) }
        }
    }

    fun startEdit(patient: Patient) {
        editingPatientId = patient.id
        gender = patient.gender
        species = patient.species
        _uiState.update {
            it.copy(
                nombre = patient.name,
                peso = patient.weight.toString(),
                edad = patient.age,
                dueno = patient.owner,
                telefono = patient.telephone,
                descripcion = patient.description
            )
        }
    }

    fun deletePatient(token: String, patient: Patient) {
        viewModelScope.launch {
            deletePatientUseCase(token, patient.id)
            loadPatients(token)
        }
    }

    fun savePatient(
        token: String,
        branchId: Int,
        userId: Int,
        imageUrl: String = "",
        onSuccess: () -> Unit
    ) {
        val s = _uiState.value

        if (
            s.nombre.isEmpty() ||
            s.peso.isEmpty() ||
            s.edad.isEmpty() ||
            s.dueno.isEmpty() ||
            s.telefono.isEmpty() ||
            gender.isEmpty() ||
            species.isEmpty()
        ) {
            _uiState.update { it.copy(mensajeError = "Fill in all fields") }
            return
        }

        val request = PatientRequest(
            branchId = branchId,
            userId = userId,
            name = s.nombre,
            species = species,
            description = s.descripcion,
            gender = gender,
            weight = s.peso.toDoubleOrNull() ?: 0.0,
            age = s.edad,
            owner = s.dueno,
            telephone = s.telefono,
            imageUrl = imageUrl
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val success = if (editingPatientId == null) {
                addPatientUseCase(token, request)
            } else {
                updatePatientUseCase(token, editingPatientId!!, request)
            }

            if (success) {
                clearForm()
                loadPatients(token)
                onSuccess()
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error saving patient") }
            }
        }
    }

    fun clearForm() {
        editingPatientId = null
        gender = ""
        species = ""
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