package com.arthur.rukiasvet.features.product.data.datasources.remote.mapper

import com.arthur.rukiasvet.features.product.data.model.ProductResponse
import com.arthur.rukiasvet.features.product.domain.model.Product

fun ProductResponse.toDomain() = Product(
    id          = id,
    name        = name,
    description = description,
    price       = price,
    stock       = stock,
    category    = category,
    branchId    = branchId,
    imageUrl    = imageUrl
)

fun List<ProductResponse>.toDomain() = map { it.toDomain() }