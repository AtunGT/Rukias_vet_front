package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class DeletePatientUseCase(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(token: String, id: Int): Boolean {
        return repository.deletePatient(token, id)
    }
}
