package com.arthur.rukiasvet.features.patient.domain.repositories

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient

interface PatientRepository {
    suspend fun addPatient(token: String, paciente: PatientRequest): Boolean
    suspend fun getPatients(token: String): List<Patient>
    suspend fun deletePatient(token: String, id: Int): Boolean
    suspend fun updatePatient(token: String, id: Int, paciente: PatientRequest): Boolean
}
