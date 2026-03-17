package com.arthur.rukiasvet.features.branch.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arthur.rukiasvet.core.permissions.rememberPermissionManager
import com.arthur.rukiasvet.core.utils.HapticHelper
import com.arthur.rukiasvet.features.branch.domain.model.Branch
import com.arthur.rukiasvet.features.branch.presentation.components.BranchForm
import com.arthur.rukiasvet.features.branch.presentation.components.BranchList
import com.arthur.rukiasvet.features.branch.presentation.viewmodels.BranchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchListScreen(
    state: BranchUIState,
    onBranchClick: (Int) -> Unit,
    onAddBranchClick: () -> Unit,
    onDeleteBranch: (Branch) -> Unit,
    onEditBranch: (Branch) -> Unit,
    viewModel: BranchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showForm by remember { mutableStateOf(false) }

    val permissionManager = rememberPermissionManager(
        onCameraGranted = {},
        onGalleryGranted = {},
        onLocationGranted = {
            viewModel.getCurrentLocation { address ->
                address?.let { viewModel.onAddressChange(it) }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "RukiasVet", style = MaterialTheme.typography.headlineSmall, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar sucursal")
            }
        },
        containerColor = Color.White
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                state.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                state.branches.isEmpty() -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No hay sucursales. Agrega una con el botón +") }
                else -> BranchList(
                    branches = state.branches,
                    onBranchClick = onBranchClick,
                    onEdit = { branch -> viewModel.startEdit(branch); showForm = true },
                    onDelete = onDeleteBranch
                )
            }
        }

        if (showForm) {
            Dialog(onDismissRequest = { showForm = false; viewModel.clearForm() }) {
                BranchForm(
                    name = state.name,
                    onNameChange = viewModel::onNameChange,
                    address = state.address,
                    onAddressChange = viewModel::onAddressChange,
                    onGuardarClick = {
                        viewModel.saveBranch {
                            HapticHelper.vibrate(context)
                            showForm = false
                        }
                    },
                    onCerrarClick = { showForm = false; viewModel.clearForm() },
                    onGetLocationClick = { permissionManager.onRequestLocation() },
                    isLoading = state.isLoading,
                    errorMessage = state.errorMessage
                )
            }
        }
    }
}