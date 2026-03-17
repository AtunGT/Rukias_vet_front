package com.arthur.rukiasvet.features.product.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthur.rukiasvet.core.components.EmptyState
import com.arthur.rukiasvet.core.components.LoadingBox
import com.arthur.rukiasvet.features.product.domain.model.Product
import com.arthur.rukiasvet.features.product.presentation.components.ProductItem
import com.arthur.rukiasvet.features.product.presentation.viewmodels.ProductViewModel

@Composable
fun ProductListScreen(
    branchId: Int,
    onEditProduct: (Product) -> Unit,
    viewModel: ProductViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(branchId) {
        viewModel.loadProductsByBranch(branchId)
    }

    when {
        state.isLoading -> LoadingBox()
        state.listaProductos.isEmpty() -> EmptyState()
        else -> ProductList(
            products = state.listaProductos,
            onEdit = onEditProduct,
            onDelete = { product ->
                viewModel.deleteProduct(product, branchId)
            }
        )
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onEdit: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onEditClick = { onEdit(product) },
                onDeleteClick = { onDelete(product) }
            )
        }
    }
}