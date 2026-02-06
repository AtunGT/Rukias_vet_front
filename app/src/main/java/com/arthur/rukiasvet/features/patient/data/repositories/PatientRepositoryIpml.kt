package com.arthur.rukiasvet.features.patient.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.data.model.PatientResponse
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class PatientRepositoryImpl(
    private val api: Api_Veterinaria
) : PatientRepository {

    override suspend fun addPatient(token: String, paciente: PatientRequest): Boolean {
        return try {
            val header = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val response = api.agregarPaciente(header, paciente)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getPatients(token: String): List<PatientResponse> {
        return try {
            val header = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val response = api.obtenerPacientes(header)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}