package com.arthur.rukiasvet.features.product.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arthur.rukiasvet.core.components.EmptyState
import com.arthur.rukiasvet.core.components.LoadingBox
import com.arthur.rukiasvet.features.product.domain.model.Product
import com.arthur.rukiasvet.features.product.presentation.components.ProductItem
import com.arthur.rukiasvet.features.product.presentation.viewmodels.ProductViewModel

@Composable
fun ProductListScreen(
    branchId: Int,
    onAddProductClick: () -> Unit,
    onEditProduct: (Product) -> Unit,
    viewModel: ProductViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(branchId) {
        viewModel.loadProductsByBranch(branchId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingBox()
            state.listaProductos.isEmpty() -> EmptyState()
            else -> LazyColumn(
                modifier            = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.listaProductos) { product ->
                    ProductItem(
                        product      = product,
                        onEditClick  = { onEditProduct(product) },
                        onDeleteClick = { viewModel.deleteProduct(product, branchId) }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick         = onAddProductClick,
            modifier        = Modifier
                .align(androidx.compose.ui.Alignment.BottomEnd)
                .padding(16.dp),
            containerColor  = Color(0xFF1E60F6),
            shape           = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar producto", tint = Color.White)
        }
    }
}