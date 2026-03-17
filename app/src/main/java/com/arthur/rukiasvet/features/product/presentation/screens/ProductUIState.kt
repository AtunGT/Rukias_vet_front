package com.arthur.rukiasvet.features.product.presentation.screens

import com.arthur.rukiasvet.features.product.domain.model.Product

data class ProductUIState(
    val isLoading: Boolean    = false,
    val listaProductos: List<Product> = emptyList(),
    val nombre: String        = "",
    val descripcion: String   = "",
    val precio: String        = "",
    val stock: String         = "",
    val categoria: String     = "",
    val mensajeError: String  = "",
    val mensajeExito: Boolean = false
)