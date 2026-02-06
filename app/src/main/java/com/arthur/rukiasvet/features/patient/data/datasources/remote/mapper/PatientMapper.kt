package com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient

fun PatientRequest.toDomain(): Patient {
    return Patient(
        id = this.id ?: 0,
        name = this.name,
        species = this.species,
        description = this.description,
        gender = this.gender,
        weight = this.weight,
        age = this.age,
        owner = this.owner,
        telephone = this.telephone
    )
}

fun List<PatientRequest>.toDomain(): List<Patient> {
    return this.map { it.toDomain() }
}