package com.arthur.rukiasvet.features.branch.domain.repositories

import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.model.Branch

interface BranchRepository {
    suspend fun addBranch(token: String, branch: BranchRequest): Boolean
    suspend fun getAllBranches(token: String): List<Branch>
    suspend fun deleteBranch(token: String, id: Int): Boolean
    suspend fun updateBranch(token: String, id: Int, branch: BranchRequest): Boolean
}