package com.arthur.rukiasvet.features.patient.domain.repositories

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest


interface PatientRepository {

    suspend fun addPatient(
        token: String,
        patient: PatientRequest
    ): Boolean

    suspend fun getPatients(
        token: String
    ): List<PatientRequest>

    suspend fun deletePatient(
        token: String,
        id: Int
    ): Boolean

    suspend fun updatePatient(
        token: String,
        id: Int,
        patient: PatientRequest
    ): Boolean
}