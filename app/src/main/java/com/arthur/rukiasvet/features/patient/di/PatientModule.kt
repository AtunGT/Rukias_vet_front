package com.arthur.rukiasvet.features.patient.di

import com.arthur.rukiasvet.core.di.AppContainer
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModelFactory

class PatientModule(private val appContainer: AppContainer) {

    fun provideViewModelFactory(): PatientViewModelFactory {
        return PatientViewModelFactory(appContainer.patientRepository)
    }
}