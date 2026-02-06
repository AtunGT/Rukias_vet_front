package com.arthur.rukiasvet.features.patient.domain.repositories

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientResponse

interface PatientRepository {
    suspend fun addPatient(token: String, paciente: PatientRequest): Boolean
    suspend fun getPatients(token: String): List<PatientResponse>
}