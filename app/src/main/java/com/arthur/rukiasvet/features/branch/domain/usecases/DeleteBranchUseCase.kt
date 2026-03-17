package com.arthur.rukiasvet.features.branch.domain.usecases

import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import javax.inject.Inject

class DeleteBranchUseCase @Inject constructor(
    private val repository: BranchRepository
) {
    suspend operator fun invoke(token: String, id: Int): Boolean =
        repository.deleteBranch(token, id)
}