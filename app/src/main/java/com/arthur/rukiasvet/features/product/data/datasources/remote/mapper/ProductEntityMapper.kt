package com.arthur.rukiasvet.features.product.data.datasources.remote.mapper

import com.arthur.rukiasvet.core.database.entities.ProductEntity
import com.arthur.rukiasvet.features.product.domain.model.Product

fun Product.toEntity() = ProductEntity(
    id = id, name = name, description = description,
    price = price, stock = stock, category = category,
    branchId = branchId, imageUrl = imageUrl ?: ""
)

fun ProductEntity.toDomain() = Product(
    id = id, name = name, description = description,
    price = price, stock = stock, category = category,
    branchId = branchId, imageUrl = imageUrl
)