package com.arthur.rukiasvet.features.branch.data.datasources.remote.mapper

import com.arthur.rukiasvet.core.database.entities.BranchEntity
import com.arthur.rukiasvet.features.branch.domain.model.Branch

fun Branch.toEntity() = BranchEntity(
    id = id,
    name = name,
    address = address
)

fun BranchEntity.toDomain() = Branch(
    id = id,
    name = name,
    address = address
)