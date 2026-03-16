package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class AddPatientUseCase @Inject constructor(private val repository: PatientRepository) {

    suspend operator fun invoke(token: String, patient: PatientRequest): Boolean {
        return repository.addPatient(token, patient)
    }
}