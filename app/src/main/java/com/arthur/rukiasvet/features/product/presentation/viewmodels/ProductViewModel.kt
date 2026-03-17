package com.arthur.rukiasvet.features.product.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.rukiasvet.core.session.SessionRepository
import com.arthur.rukiasvet.features.product.data.model.ProductRequest
import com.arthur.rukiasvet.features.product.domain.model.Product
import com.arthur.rukiasvet.features.product.domain.usecases.AddProductUseCase
import com.arthur.rukiasvet.features.product.domain.usecases.DeleteProductUseCase
import com.arthur.rukiasvet.features.product.domain.usecases.GetProductByIdUseCase
import com.arthur.rukiasvet.features.product.domain.usecases.GetProductsByBranchUseCase
import com.arthur.rukiasvet.features.product.domain.usecases.UpdateProductUseCase
import com.arthur.rukiasvet.features.product.presentation.screens.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val getProductsByBranchUseCase: GetProductsByBranchUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    private val _capturedImageFile = MutableStateFlow<File?>(null)
    val capturedImageFile: StateFlow<File?> = _capturedImageFile.asStateFlow()

    var editingProductId: Int? = null
        private set

    fun onNameChange(value: String)        { _uiState.update { it.copy(nombre = value) } }
    fun onDescriptionChange(value: String) { _uiState.update { it.copy(descripcion = value) } }
    fun onPriceChange(value: String)       { _uiState.update { it.copy(precio = value) } }
    fun onStockChange(value: String)       { _uiState.update { it.copy(stock = value) } }
    fun onCategoryChange(value: String)    { _uiState.update { it.copy(categoria = value) } }

    fun setCapturedImage(file: File) { _capturedImageFile.value = file }
    fun clearImage()                 { _capturedImageFile.value = null }

    fun loadProductsByBranch(branchId: Int) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: run {
                _uiState.update { it.copy(mensajeError = "Sesión no válida") }
                return@launch
            }
            Log.d("ProductVM", "branchId: $branchId")
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }
            val list = getProductsByBranchUseCase(token, branchId)
            Log.d("ProductVM", "productos recibidos: ${list.size}")
            _uiState.update { it.copy(isLoading = false, listaProductos = list) }
        }
    }

    fun startEdit(product: Product) {
        editingProductId = product.id
        _uiState.update {
            it.copy(
                nombre      = product.name,
                descripcion = product.description,
                precio      = product.price.toString(),
                stock       = product.stock.toString(),
                categoria   = product.category
            )
        }
    }

    fun loadProductForEdit(id: Int) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: return@launch
            val product = getProductByIdUseCase(token, id)
            product?.let { startEdit(it) }
                ?: _uiState.update { it.copy(mensajeError = "No se pudo cargar el producto") }
        }
    }

    fun deleteProduct(product: Product, branchId: Int? = null) {
        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: return@launch
            _uiState.update { it.copy(isLoading = true) }
            val success = deleteProductUseCase(token, product.id)
            if (success) {
                if (branchId != null) loadProductsByBranch(branchId)
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al eliminar") }
            }
        }
    }

    fun saveProduct(branchId: Int, onSuccess: () -> Unit) {
        val s = _uiState.value
        if (!validateFields(s)) return

        val request = ProductRequest(
            branchId    = branchId,
            name        = s.nombre,
            description = s.descripcion,
            price       = s.precio.toDoubleOrNull() ?: 0.0,
            stock       = s.stock.toIntOrNull() ?: 0,
            category    = s.categoria
        )

        viewModelScope.launch {
            val token = sessionRepository.getToken() ?: run {
                _uiState.update { it.copy(mensajeError = "Sesión no válida") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, mensajeError = "") }

            val imageFile = _capturedImageFile.value
            val success = if (editingProductId == null) {
                addProductUseCase(token, request, imageFile)
            } else {
                updateProductUseCase(token, editingProductId!!, request, imageFile)
            }

            if (success) {
                clearForm()
                loadProductsByBranch(branchId)
                onSuccess()
            } else {
                _uiState.update { it.copy(isLoading = false, mensajeError = "Error al guardar") }
            }
        }
    }

    private fun validateFields(s: ProductUIState): Boolean {
        val valid = s.nombre.isNotBlank() && s.precio.isNotBlank() &&
                s.stock.isNotBlank() && s.categoria.isNotBlank()
        if (!valid) _uiState.update { it.copy(mensajeError = "Todos los campos son obligatorios") }
        return valid
    }

    fun clearForm() {
        editingProductId = null
        clearImage()
        _uiState.update { ProductUIState() }
    }
}