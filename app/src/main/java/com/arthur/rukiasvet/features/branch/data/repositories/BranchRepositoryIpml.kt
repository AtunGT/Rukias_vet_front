package com.arthur.rukiasvet.features.branch.data.repositories

import com.arthur.rukiasvet.core.database.dao.BranchDao
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.branch.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.branch.data.datasources.remote.mapper.toEntity
import com.arthur.rukiasvet.features.branch.data.model.BranchRequest
import com.arthur.rukiasvet.features.branch.domain.model.Branch
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import javax.inject.Inject

class BranchRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria,
    private val branchDao: BranchDao
) : BranchRepository {

    override suspend fun getAllBranches(token: String): List<Branch> {
        return try {
            val response = api.getBranches("Bearer $token")
            if (response.isSuccessful) {
                val list = response.body()?.toDomain() ?: emptyList()
                branchDao.deleteAll()
                branchDao.insertAll(list.map { it.toEntity() })
                list
            } else {
                branchDao.getAll().map { it.toDomain() }
            }
        } catch (e: Exception) {
            android.util.Log.d("BranchRepo", "Sin internet, cargando desde Room")
            branchDao.getAll().map { it.toDomain() }
        }
    }

    override suspend fun addBranch(token: String, branch: BranchRequest): Boolean {
        return try {
            api.addBranch("Bearer $token", branch).isSuccessful
        } catch (e: Exception) { false }
    }

    override suspend fun deleteBranch(token: String, id: Int): Boolean {
        return try {
            api.deleteBranch("Bearer $token", id).isSuccessful
        } catch (e: Exception) { false }
    }

    override suspend fun updateBranch(token: String, id: Int, branch: BranchRequest): Boolean {
        return try {
            api.updateBranch("Bearer $token", id, branch).isSuccessful
        } catch (e: Exception) { false }
    }
}