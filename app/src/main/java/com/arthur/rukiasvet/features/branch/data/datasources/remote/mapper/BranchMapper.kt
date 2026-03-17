package com.arthur.rukiasvet.features.branch.data.datasources.remote.mapper

import com.arthur.rukiasvet.features.branch.data.model.BranchResponse
import com.arthur.rukiasvet.features.branch.domain.model.Branch

fun BranchResponse.toDomain(): Branch {
    return Branch(
        id = this.id,
        name = this.name ?: "",
        address = this.address ?: ""
    )
}fun List<BranchResponse>.toDomain(): List<Branch> = map { it.toDomain() }