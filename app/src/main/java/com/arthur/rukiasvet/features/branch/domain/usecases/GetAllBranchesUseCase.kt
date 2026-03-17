package com.arthur.rukiasvet.features.branch.domain.usecases

import com.arthur.rukiasvet.features.branch.domain.model.Branch
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import javax.inject.Inject

class GetAllBranchesUseCase @Inject constructor(
    private val repository: BranchRepository
) {
    suspend operator fun invoke(token: String): List<Branch> =
        repository.getAllBranches(token)
}