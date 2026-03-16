package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arthur.rukiasvet.features.patient.presentation.components.PatientForm
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun PatientScreen(
    vm: PatientViewModel,
    token: String,
    branchId: Int = 0,
    userId: Int = 0,
    onClose: () -> Unit
) {
    val state by vm.uiState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PatientForm(
                nombre = state.nombre,
                onNombreChange = vm::onNameChange,
                peso = state.peso,
                onPesoChange = vm::onWeightChange,
                edad = state.edad,
                onEdadChange = vm::onAgeChange,
                genero = vm.gender,
                onGeneroChange = vm::onGenderChange,
                especie = vm.species,
                onEspecieChange = vm::onSpeciesChange,
                dueno = state.dueno,
                onDuenoChange = vm::onOwnerChange,
                telefono = state.telefono,
                onTelefonoChange = vm::onPhoneChange,
                descripcion = state.descripcion,
                onDescripcionChange = vm::onDescriptionChange,
                onGuardarClick = {
                    vm.savePatient(
                        token = token,
                        branchId = branchId,
                        userId = userId,
                        onSuccess = onClose
                    )
                },
                onCerrarClick = onClose,
                isLoading = state.isLoading,
                mensajeError = state.mensajeError
            )
        }
    }
}