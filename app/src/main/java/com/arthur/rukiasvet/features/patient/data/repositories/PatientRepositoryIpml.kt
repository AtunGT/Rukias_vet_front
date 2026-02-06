package com.arthur.rukiasvet.features.patient.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
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
            e.printStackTrace()
            false
        }
    }

    override suspend fun getPatients(token: String): List<Patient> {
        return try {
            val header = if (token.startsWith("Bearer ")) token else "Bearer $token"

            val response = api.obtenerPacientes(header)

            if (response.isSuccessful) {
                val requests = response.body() ?: emptyList()
                requests.toDomain()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}