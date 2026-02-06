package com.arthur.rukiasvet.features.patient.di

import com.arthur.rukiasvet.core.di.AppContainer
import com.arthur.rukiasvet.features.patient.domain.usecases.AddPatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.DeletePatientUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.GetAllPatientsUseCase
import com.arthur.rukiasvet.features.patient.domain.usecases.UpdatePatientUseCase
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModelFactory

class PatientModule(
    private val appContainer: AppContainer
) {

    fun provideViewModelFactory(): PatientViewModelFactory {
        val repository = appContainer.patientRepository

        return PatientViewModelFactory(
            addPatientUseCase = AddPatientUseCase(repository),
            getAllPatientsUseCase = GetAllPatientsUseCase(repository),
            deletePatientUseCase = DeletePatientUseCase(repository),
            updatePatientUseCase = UpdatePatientUseCase(repository)
        )
    }
}
