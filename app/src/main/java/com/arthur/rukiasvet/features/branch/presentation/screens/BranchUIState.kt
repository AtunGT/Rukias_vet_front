package com.arthur.rukiasvet.features.branch.presentation.screens

import com.arthur.rukiasvet.features.branch.domain.model.Branch

data class BranchUIState(
    val name: String = "",
    val address: String = "",
    val branches: List<Branch> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)