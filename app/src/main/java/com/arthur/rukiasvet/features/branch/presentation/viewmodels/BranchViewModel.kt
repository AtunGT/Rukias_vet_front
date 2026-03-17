package com.arthur.rukiasvet.features.branch.presentation.viewmodels

import com.arthur.rukiasvet.core.hardware.location.LocationRepository
import com.arthur.rukiasvet.core.hardware.location.model.Location
import com.arthur.rukiasvet.core.session.SessionManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.model.Branch
import com.arthur.rukiasvet.features.branch.domain.usecases.AddBranchUseCase
import com.arthur.rukiasvet.features.branch.domain.usecases.DeleteBranchUseCase
import com.arthur.rukiasvet.features.branch.domain.usecases.GetAllBranchesUseCase
import com.arthur.rukiasvet.features.branch.domain.usecases.UpdateBranchUseCase
import com.arthur.rukiasvet.features.branch.presentation.screens.BranchUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val addBranchUseCase: AddBranchUseCase,
    private val getAllBranchesUseCase: GetAllBranchesUseCase,
    private val deleteBranchUseCase: DeleteBranchUseCase,
    private val updateBranchUseCase: UpdateBranchUseCase,
    private val locationRepository: LocationRepository,
    private val sessionManager: SessionManager  // ← AÑADIDO
) : ViewModel() {

    private val _uiState = MutableStateFlow(BranchUIState())
    val uiState: StateFlow<BranchUIState> = _uiState.asStateFlow()

    val token: StateFlow<String?> = sessionManager.token
    val userId: StateFlow<Int?> = sessionManager.userId
    val isAuthenticated: StateFlow<Boolean> = sessionManager.isAuthenticated

    var editingBranchId: Int? = null

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onAddressChange(value: String) {
        _uiState.update { it.copy(address = value) }
    }

    fun loadBranches() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val currentToken = token.value
                if (currentToken == null) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "No autenticado") }
                    return@launch
                }

                val list = getAllBranchesUseCase(currentToken)
                _uiState.update { it.copy(isLoading = false, branches = list) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun startEdit(branch: Branch) {
        editingBranchId = branch.id
        _uiState.update { it.copy(name = branch.name, address = branch.address) }
    }

    fun deleteBranch(branch: Branch) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentToken = token.value
                if (currentToken == null) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "No autenticado") }
                    return@launch
                }

                val success = deleteBranchUseCase(currentToken, branch.id)
                if (success) {
                    loadBranches()
                    _uiState.update { it.copy(successMessage = "Sucursal eliminada") }
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Error al eliminar") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun saveBranch(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.name.isBlank() || state.address.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios") }
            return
        }

        val request = BranchRequest(
            name = state.name,
            address = state.address
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val currentToken = token.value
                if (currentToken == null) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "No autenticado") }
                    return@launch
                }

                val success = if (editingBranchId == null) {
                    addBranchUseCase(currentToken, request)
                } else {
                    updateBranchUseCase(currentToken, editingBranchId!!, request)
                }

                if (success) {
                    clearForm()
                    loadBranches()
                    onSuccess()
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Error al guardar") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun clearForm() {
        editingBranchId = null
        _uiState.update { it.copy(name = "", address = "", errorMessage = null, successMessage = null) }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

    fun getCurrentLocation(onLocationResult: (String?) -> Unit) {
        viewModelScope.launch {
            val result = locationRepository.getCurrentLocation()
            result.onSuccess { location ->
                val address = locationRepository.getAddressFromLocation(
                    location.latitude,
                    location.longitude
                )
                onLocationResult(address)
            }.onFailure {
                _uiState.update { it.copy(errorMessage = "Error al obtener ubicación") }
                onLocationResult(null)
            }
        }
    }
}