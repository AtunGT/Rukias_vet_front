package com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper

import com.arthur.rukiasvet.features.patient.data.model.PatientResponse
import com.arthur.rukiasvet.features.patient.domain.model.Patient

fun PatientResponse.toDomain(): Patient {
    return Patient(
        id = this.id ?: 0,
        name = this.name,
        species = this.species,
        description = this.description,
        gender = this.gender,
        weight = this.weight,
        age = this.age,
        owner = this.owner,
        telephone = this.telephone,
        branchId = this.branchId,
        imageUrl = this.imageUrl
    )
}

fun List<PatientResponse>.toDomain(): List<Patient> = map { it.toDomain() }