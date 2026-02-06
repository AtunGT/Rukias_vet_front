package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import com.arthur.rukiasvet.features.patient.domain.usecases.AddPatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetAllPatientsUseCase

class PatientViewModelFactory(
    private val addPatientUseCase: AddPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(addPatientUseCase, getAllPatientsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}