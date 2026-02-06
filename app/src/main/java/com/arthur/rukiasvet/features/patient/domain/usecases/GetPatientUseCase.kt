package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class GetAllPatientsUseCase(private val repository: PatientRepository) {

    suspend operator fun invoke(token: String): List<Patient> {
        return repository.getPatients(token)
    }
}