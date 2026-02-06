package com.arthur.rukiasvet.features.patient.presentation.viewmodels

import com.arthur.rukiasvet.features.patient.domain.model.Patient

data class PatientUIState(
    val nombre: String = "",
    val peso: String = "",
    val edad: String = "",
    val dueno: String = "",
    val telefono: String = "",
    val descripcion: String = "",

    val listaPacientes: List<Patient> = emptyList(),

    val isLoading: Boolean = false,
    val mensajeExito: Boolean = false,
    val mensajeError: String = ""
)