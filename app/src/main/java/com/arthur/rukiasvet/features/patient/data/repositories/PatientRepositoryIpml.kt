package com.arthur.rukiasvet.features.patient.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria
) : PatientRepository {

    override suspend fun addPatient(token: String, patient: PatientRequest): Boolean {
        val response = api.addPatient(token, patient)
        return response.isSuccessful
    }

    override suspend fun getPatients(token: String): List<PatientRequest> {
        val response = api.getPatients(token)
        return response.body() ?: emptyList()
    }

    override suspend fun deletePatient(token: String, id: Int): Boolean {
        val response = api.deletePatient(token, id)
        return response.isSuccessful
    }

    override suspend fun updatePatient(token: String, id: Int, patient: PatientRequest): Boolean {
        val response = api.updatePatient(token, id, patient)
        return response.isSuccessful
    }
}