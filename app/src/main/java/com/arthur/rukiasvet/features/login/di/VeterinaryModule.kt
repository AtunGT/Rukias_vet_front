package com.arthur.rukiasvet.features.login.di

import com.arthur.rukiasvet.core.di.AppContainer
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryViewModelFactory

class VeterinaryModule(private val appContainer: AppContainer) {

    fun provideViewModelFactory(): VeterinaryViewModelFactory {
        return VeterinaryViewModelFactory(appContainer.veterinaryRepository)
    }
}