package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class AddPatientUseCase(private val repository: PatientRepository) {

    suspend operator fun invoke(token: String, paciente: PatientRequest): Boolean {
        return repository.addPatient(token, paciente)
    }
}