package com.arthur.rukiasvet.features.branch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientList
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientUIState
import com.arthur.rukiasvet.features.product.domain.model.Product
import com.arthur.rukiasvet.features.product.presentation.screens.ProductListScreen
import com.arthur.rukiasvet.features.product.presentation.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchDetailScreen(
    branchId: Int,
    patientState: PatientUIState,
    productViewModel: ProductViewModel,
    onAddPatientClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onEditPatient: (Patient) -> Unit,
    onEditProduct: (Product) -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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
                    ProductListScreen(
                        branchId = branchId,
                        onAddProductClick = onAddProductClick,
                        onEditProduct = onEditProduct,
                        viewModel = productViewModel
                    )
                }
            }
        }
    }
}