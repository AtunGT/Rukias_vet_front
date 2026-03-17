package com.arthur.rukiasvet.features.patient.domain.repositories

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import java.io.File

interface PatientRepository {
    suspend fun addPatient(
        token: String,
        patient: PatientRequest,
        imageFile: File? = null
    ): Boolean

    suspend fun updatePatient(
        token: String,
        id: Int,
        patient: PatientRequest,
        imageFile: File? = null
    ): Boolean

    suspend fun getPatients(token: String): List<Patient>
    suspend fun getPatientsByBranch(token: String, branchId: Int): List<Patient>
    suspend fun deletePatient(token: String, id: Int): Boolean

    suspend fun getPatientById(token: String, id: Int): Patient?

}