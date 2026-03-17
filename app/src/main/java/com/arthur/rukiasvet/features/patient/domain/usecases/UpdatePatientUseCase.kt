package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import java.io.File
import javax.inject.Inject

class UpdatePatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
        patient: PatientRequest,
        imageFile: File? = null
    ): Boolean = repository.updatePatient(token, id, patient, imageFile)
}