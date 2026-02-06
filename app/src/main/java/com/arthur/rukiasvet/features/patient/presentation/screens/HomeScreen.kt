package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.presentation.components.PatientItem
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel
import com.arthur.rukiasvet.features.patient.presentation.components.EmptyState
import com.arthur.rukiasvet.features.patient.presentation.components.HomeHeader
import com.arthur.rukiasvet.features.patient.presentation.components.LoadingBox

@Composable
fun HomeScreen(
    nombreUsuario: String,
    token: String,
    patientVm: PatientViewModel,
    onCerrarSesion: () -> Unit,
    onAddPatientClick: () -> Unit
) {
    val state by patientVm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        patientVm.cargarPacientes(token)
    }

    Scaffold(
        containerColor = Color(0xFFEEF2FA),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    patientVm.limpiarFormulario()
                    onAddPatientClick()
                },
                containerColor = Color(0xFF1E60F6),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            HomeHeader(nombreUsuario, onCerrarSesion)

            Column(modifier = Modifier.padding(20.dp)) {

                PatientCounter(count = state.listaPacientes.size)

                Spacer(modifier = Modifier.height(20.dp))

                when {
                    state.isLoading -> LoadingBox()
                    state.listaPacientes.isEmpty() -> EmptyState()
                    else -> PatientList(
                        pacientes = state.listaPacientes,
                        onEdit = { p ->
                            patientVm.startEdit(p)
                            onAddPatientClick()
                        },
                        onDelete = { p -> patientVm.deletePatient(token, p) }
                    )
                }
            }
        }
    }
}

@Composable
fun PatientCounter(count: Int) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$count pacientes registrados",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1E60F6)
        )
    }
}

@Composable
fun PatientList(
    pacientes: List<Patient>,
    onEdit: (Patient) -> Unit,
    onDelete: (Patient) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pacientes) { paciente ->
            PatientItem(
                paciente = paciente,
                onEditClick = { onEdit(paciente) },
                onDeleteClick = { onDelete(paciente) }
            )
        }
    }
}