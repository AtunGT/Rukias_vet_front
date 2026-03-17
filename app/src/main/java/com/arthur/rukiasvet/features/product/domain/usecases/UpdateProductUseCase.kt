package com.arthur.rukiasvet.features.product.domain.usecases

import com.arthur.rukiasvet.features.product.data.model.ProductRequest
import com.arthur.rukiasvet.features.product.domain.repositories.ProductRepository
import java.io.File
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(token: String, id: Int, product: ProductRequest, imageFile: File?) =
        repository.updateProduct(token, id, product, imageFile)
}