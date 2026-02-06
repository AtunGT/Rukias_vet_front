package com.arthur.rukiasvet.features.login.presentation.viewmodels

data class VeterinaryUIState(
    val nombreUsuario: String = "",
    val esRegistro: Boolean = false,
    val diagnosticoReal: String = "",
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val mensajeError: String = "",
    val mensajeExito: String = ""
)