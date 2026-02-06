package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arthur.rukiasvet.features.patient.domain.usecases.AddPatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.DeletePatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetAllPatientsUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.UpdatePatientUseCase

class PatientViewModelFactory(
    private val addPatientUseCase: AddPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val deletePatientUseCase: DeletePatientUseCase,
    private val updatePatientUseCase: UpdatePatientUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            return PatientViewModel(
                addPatientUseCase = addPatientUseCase,
                getAllPatientsUseCase = getAllPatientsUseCase,
                deletePatientUseCase = deletePatientUseCase,
                updatePatientUseCase = updatePatientUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
