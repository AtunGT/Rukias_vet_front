package com.arthur.rukiasvet.features.branch.domain.usecases

import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class UpdateBranchUseCase @Inject constructor(
    private val repository: BranchRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
        branch: BranchRequest
    ): Boolean {
        return repository.updateBranch(token, id, branch)
    }
}