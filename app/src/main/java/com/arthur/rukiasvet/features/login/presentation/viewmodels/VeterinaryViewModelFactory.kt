package com.arthur.rukiasvet.features.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository

class VeterinaryViewModelFactory(
    private val repository: VeterinaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VeterinaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VeterinaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}