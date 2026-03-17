package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class GetAllPatientsUseCase @Inject constructor(private val repository: PatientRepository) {

    suspend operator fun invoke(token: String): List<Patient> {
        return repository.getPatients(token)
    }
}