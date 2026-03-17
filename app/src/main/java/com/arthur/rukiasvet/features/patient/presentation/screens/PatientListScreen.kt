package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.core.components.LoadingBox
import com.arthur.rukiasvet.core.components.EmptyState
import com.arthur.rukiasvet.features.patient.presentation.components.PatientItem
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun PatientListScreen(
    branchId: Int? = null,
    onAddPatientClick: () -> Unit,
    onEditPatient: (Patient) -> Unit,
    viewModel: PatientViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(branchId) {
        if (branchId != null) {
            viewModel.loadPatientsByBranch(branchId)
        } else {
            viewModel.loadPatients()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPatientClick,
                containerColor = Color(0xFF1E60F6),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar paciente", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Pacientes",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    state.isLoading -> LoadingBox()
                    state.listaPacientes.isEmpty() -> EmptyState()
                    else -> PatientList(
                        patients = state.listaPacientes,
                        onEdit = onEditPatient,
                        onDelete = { patient ->
                            viewModel.deletePatient(patient)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PatientList(
    patients: List<Patient>,
    onEdit: (Patient) -> Unit,
    onDelete: (Patient) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(patients) { patient ->
            PatientItem(
                patient = patient,
                onEditClick = { onEdit(patient) },
                onDeleteClick = { onDelete(patient) }
            )
        }
    }
}