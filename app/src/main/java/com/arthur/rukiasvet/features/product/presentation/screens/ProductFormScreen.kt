package com.arthur.rukiasvet.features.product.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arthur.rukiasvet.core.utils.HapticHelper
import com.arthur.rukiasvet.features.product.presentation.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    vm: ProductViewModel,
    branchId: Int,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (vm.editingProductId == null) "Nuevo Producto" else "Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = state.nombre, onValueChange = vm::onNameChange, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = state.descripcion, onValueChange = vm::onDescriptionChange, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), minLines = 2, maxLines = 4)
            OutlinedTextField(value = state.precio, onValueChange = vm::onPriceChange, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
            OutlinedTextField(value = state.stock, onValueChange = vm::onStockChange, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            OutlinedTextField(value = state.categoria, onValueChange = vm::onCategoryChange, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

            if (state.mensajeError.isNotBlank()) {
                Text(text = state.mensajeError, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) {
                    Text("Cancelar")
                }
                Button(
                    onClick = {
                        vm.saveProduct(
                            branchId = branchId,
                            onSuccess = {
                                HapticHelper.vibrate(context)
                                onClose()
                            }
                        )
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}