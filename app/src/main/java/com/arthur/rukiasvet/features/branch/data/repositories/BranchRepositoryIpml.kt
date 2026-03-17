package com.arthur.rukiasvet.features.branch.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.branch.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.model.Branch
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import javax.inject.Inject

class BranchRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria
) : BranchRepository {

    override suspend fun addBranch(token: String, branch: BranchRequest): Boolean {
        val response = api.addBranch("Bearer $token", branch)
        return response.isSuccessful
    }


    override suspend fun getAllBranches(token: String): List<Branch> {
        return try {
            val response = api.getBranches("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteBranch(token: String, id: Int): Boolean {
        val response = api.deleteBranch("Bearer $token", id)
        return response.isSuccessful
    }

    override suspend fun updateBranch(token: String, id: Int, branch: BranchRequest): Boolean {
        val response = api.updateBranch("Bearer $token", id, branch)
        return response.isSuccessful
    }
}