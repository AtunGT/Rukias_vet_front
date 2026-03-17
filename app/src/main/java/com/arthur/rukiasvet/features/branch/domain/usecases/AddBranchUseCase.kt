package com.arthur.rukiasvet.features.branch.domain.usecases

import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import javax.inject.Inject

class AddBranchUseCase @Inject constructor(
    private val repository: BranchRepository
) {
    suspend operator fun invoke(token: String, branch: BranchRequest): Boolean =
        repository.addBranch(token, branch)
}
