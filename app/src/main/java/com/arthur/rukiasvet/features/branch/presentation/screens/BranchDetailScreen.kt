package com.arthur.rukiasvet.features.branch.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Alignment
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientList
import com.arthur.rukiasvet.features.branch.presentation.screens.BranchUIState
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchDetailScreen(
    branchId: Int,
    patientState: PatientUIState,
    onAddPatientClick: () -> Unit,
    onEditPatient: (Patient) -> Unit,
    onDeletePatient: (Patient) -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pacientes", "Productos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Sucursal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = onAddPatientClick) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar paciente")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    when {
                        patientState.isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        patientState.listaPacientes.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No hay pacientes. Agrega uno con el botón +")
                            }
                        }
                        else -> {
                            PatientList(
                                patients = patientState.listaPacientes,
                                onEdit = onEditPatient,
                                onDelete = onDeletePatient
                            )
                        }
                    }
                }
                1 -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text("Productos (próximamente)")
                    }
                }
            }
        }
    }
}