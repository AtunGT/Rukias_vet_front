
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
import com.arthur.rukiasvet.features.patient.presentation.components.PatientCard
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun PatientScreen(
    vm: PatientViewModel,
    token: String,
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
            PatientCard(
                nombre = state.nombre,
                onNombreChange = vm::onNombreChange,
                peso = state.peso,
                onPesoChange = vm::onPesoChange,
                edad = state.edad,
                onEdadChange = vm::onEdadChange,
                genero = vm.genero,
                onGeneroChange = vm::onGeneroChange,
                especie = vm.especie,
                onEspecieChange = vm::onEspecieChange,
                dueno = state.dueno,
                onDuenoChange = vm::onDuenoChange,
                telefono = state.telefono,
                onTelefonoChange = vm::onTelefonoChange,
                descripcion = state.descripcion,
                onDescripcionChange = vm::onDescripcionChange,
                onGuardarClick = {
                    vm.guardarPaciente(token, onSuccess = onClose)
                },
                onCerrarClick = onClose,
                isLoading = state.isLoading,
                mensajeError = state.mensajeError
            )
        }
    }
}
