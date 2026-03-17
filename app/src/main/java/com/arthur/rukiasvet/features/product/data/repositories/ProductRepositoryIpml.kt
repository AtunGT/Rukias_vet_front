package com.arthur.rukiasvet.features.product.data.repositories

import android.util.Log
import com.arthur.rukiasvet.core.database.dao.ProductDao
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.product.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.product.data.model.ProductRequest
import com.arthur.rukiasvet.features.product.domain.model.Product
import com.arthur.rukiasvet.features.product.domain.repositories.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun addProduct(token: String, product: ProductRequest, imageFile: File?): Boolean {
        return try {
            val namePart        = product.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionPart = product.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val pricePart       = product.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val stockPart       = product.stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryPart    = product.category.toRequestBody("text/plain".toMediaTypeOrNull())
            val branchIdPart    = product.branchId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageFile?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", it.name, requestFile)
            }

            val response = api.addProduct(
                token       = "Bearer $token",
                name        = namePart,
                description = descriptionPart,
                price       = pricePart,
                stock       = stockPart,
                category    = categoryPart,
                branchId    = branchIdPart,
                image       = imagePart
            )
            Log.d("ProductRepo", "Add code: ${response.code()}")
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("ProductRepo", "Error adding: ${e.message}")
            false
        }
    }

    override suspend fun updateProduct(token: String, id: Int, product: ProductRequest, imageFile: File?): Boolean {
        return try {
            val namePart        = product.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionPart = product.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val pricePart       = product.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val stockPart       = product.stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryPart    = product.category.toRequestBody("text/plain".toMediaTypeOrNull())
            val branchIdPart    = product.branchId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageFile?.let {
                val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", it.name, requestFile)
            }

            val response = api.updateProduct(
                token       = "Bearer $token",
                id          = id,
                name        = namePart,
                description = descriptionPart,
                price       = pricePart,
                stock       = stockPart,
                category    = categoryPart,
                branchId    = branchIdPart,
                image       = imagePart
            )
            Log.d("ProductRepo", "Update code: ${response.code()}")
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("ProductRepo", "Error updating: ${e.message}")
            false
        }
    }

    override suspend fun getProductsByBranch(token: String, branchId: Int): List<Product> {
        return try {
            val response = api.getProductsByBranch("Bearer $token", branchId)
            if (response.isSuccessful) response.body()?.toDomain() ?: emptyList()
            else emptyList()
        } catch (e: Exception) { emptyList() }
    }

    override suspend fun deleteProduct(token: String, id: Int): Boolean {
        return try {
            api.deleteProduct("Bearer $token", id).isSuccessful
        } catch (e: Exception) { false }
    }

    override suspend fun getProductById(token: String, id: Int): Product? {
        return try {
            val response = api.getProductById("Bearer $token", id)
            if (response.isSuccessful) response.body()?.toDomain() else null
        } catch (e: Exception) { null }
    }
}