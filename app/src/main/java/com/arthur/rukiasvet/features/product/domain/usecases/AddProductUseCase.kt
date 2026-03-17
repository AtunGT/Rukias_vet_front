package com.arthur.rukiasvet.features.product.domain.usecases

import com.arthur.rukiasvet.features.product.data.model.ProductRequest
import com.arthur.rukiasvet.features.product.domain.repositories.ProductRepository
import java.io.File
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(token: String, product: ProductRequest, imageFile: File?) =
        repository.addProduct(token, product, imageFile)
}