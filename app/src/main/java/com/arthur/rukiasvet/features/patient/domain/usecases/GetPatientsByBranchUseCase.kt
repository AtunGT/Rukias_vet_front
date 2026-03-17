package com.arthur.rukiasvet.features.patient.domain.usecases

import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import javax.inject.Inject

class GetPatientsByBranchUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(token: String, branchId: Int): List<Patient> =
        repository.getPatientsByBranch(token, branchId)
}