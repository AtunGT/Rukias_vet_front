package com.arthur.rukiasvet.features.product.domain.repositories

import com.arthur.rukiasvet.features.product.data.model.ProductRequest
import com.arthur.rukiasvet.features.product.domain.model.Product
import java.io.File

interface ProductRepository {
    suspend fun addProduct(token: String, product: ProductRequest, imageFile: File?): Boolean
    suspend fun updateProduct(token: String, id: Int, product: ProductRequest, imageFile: File?): Boolean
    suspend fun getProductsByBranch(token: String, branchId: Int): List<Product>
    suspend fun deleteProduct(token: String, id: Int): Boolean
    suspend fun getProductById(token: String, id: Int): Product?
}