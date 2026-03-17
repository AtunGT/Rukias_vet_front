package com.arthur.rukiasvet.features.product.domain.usecases

import com.arthur.rukiasvet.features.product.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductsByBranchUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(token: String, branchId: Int) =
        repository.getProductsByBranch(token, branchId)
}