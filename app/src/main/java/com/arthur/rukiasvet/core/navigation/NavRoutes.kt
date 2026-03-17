package com.arthur.rukiasvet.core.navigation

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Branches
@Serializable data class BranchDetail(val branchId: Int)
@Serializable data class PatientForm(val branchId: Int, val patientId: Int?)
@Serializable data class ProductForm(val branchId: Int, val productId: Int?)