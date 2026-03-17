package com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper

import com.arthur.rukiasvet.core.database.entities.PatientEntity
import com.arthur.rukiasvet.features.patient.domain.model.Patient

fun Patient.toEntity() = PatientEntity(
    id = id, name = name, species = species,
    description = description, gender = gender,
    weight = weight, age = age, owner = owner,
    telephone = telephone, branchId = branchId,
    imageUrl = imageUrl ?: ""
)

fun PatientEntity.toDomain() = Patient(
    id = id, name = name, species = species,
    description = description, gender = gender,
    weight = weight, age = age, owner = owner,
    telephone = telephone, branchId = branchId,
    imageUrl = imageUrl
)