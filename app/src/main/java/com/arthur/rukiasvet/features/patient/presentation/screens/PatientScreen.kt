package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arthur.rukiasvet.features.patient.presentation.components.PatientCard
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun PatientScreen(
    vm: PatientViewModel,
    token: String,
    onClose: () -> Unit
) {

    val state by vm.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PatientCard(
            nombre = state.nombre,
            onNombreChange = { vm.onNombreChange(it) },
            peso = state.peso,
            onPesoChange = { vm.onPesoChange(it) },
            edad = state.edad,
            onEdadChange = { vm.onEdadChange(it) },
            dueno = state.dueno,
            onDuenoChange = { vm.onDuenoChange(it) },
            telefono = state.telefono,
            onTelefonoChange = { vm.onTelefonoChange(it) },
            descripcion = state.descripcion,
            onDescripcionChange = { vm.onDescripcionChange(it) },
            onGuardarClick = { vm.guardarPaciente(token, onSuccess = onClose) },
            onCerrarClick = onClose,
            isLoading = state.isLoading,
            mensajeError = state.mensajeError
        )
    }
}