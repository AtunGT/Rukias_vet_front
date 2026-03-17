package com.arthur.rukiasvet.features.product.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val branchId: Int,
    val imageUrl: String? = null
)