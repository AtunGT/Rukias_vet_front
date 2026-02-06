package com.arthur.rukiasvet.features.patient.data.repositories

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class PatientRepositoryImpl(
    private val api: Api_Veterinaria
) : PatientRepository {

    private fun header(token: String) =
        if (token.startsWith("Bearer ")) token else "Bearer $token"

    override suspend fun addPatient(token: String, paciente: PatientRequest): Boolean =
        api.agregarPaciente(header(token), paciente).isSuccessful

    override suspend fun getPatients(token: String): List<Patient> =
        api.obtenerPacientes(header(token)).body()?.toDomain() ?: emptyList()

    override suspend fun deletePatient(token: String, id: Int): Boolean =
        api.eliminarPaciente(header(token), id).isSuccessful

    override suspend fun updatePatient(
        token: String,
        id: Int,
        paciente: PatientRequest
    ): Boolean =
        api.actualizarPaciente(header(token), id, paciente).isSuccessful
}
