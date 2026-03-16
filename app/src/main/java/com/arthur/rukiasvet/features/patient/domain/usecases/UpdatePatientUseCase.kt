package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class UpdatePatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
        patient: PatientRequest
    ): Boolean {
        return repository.updatePatient(token, id, patient)
    }
}