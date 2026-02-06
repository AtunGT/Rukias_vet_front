package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository

class UpdatePatientUseCase(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
        paciente: PatientRequest
    ): Boolean {
        return repository.updatePatient(token, id, paciente)
    }
}
