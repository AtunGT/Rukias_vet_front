package com.arthur.rukiasvet.features.login.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arthur.rukiasvet.features.login.presentation.components.RegisterCard
import com.arthur.rukiasvet.features.login.presentation.components.VeterinaryCard
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryUIState
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryViewModel
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientScreen
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun VeterinaryScreen() {
    val vm: VeterinaryViewModel = hiltViewModel()
    val state by vm.uiState.collectAsStateWithLifecycle()

    LoginOrRegisterContent(vm = vm, state = state)
}

@Composable
fun LoginOrRegisterContent(
    vm: VeterinaryViewModel,
    state: VeterinaryUIState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF2FA)),
        contentAlignment = Alignment.Center
    ) {
        if (state.esRegistro) {
            RegisterCard(
                nombre = vm.regName,
                onNombreChange = { vm.regName = it },
                apellidos = vm.regLastname,
                onApellidosChange = { vm.regLastname = it },
                email = vm.regEmail,
                onEmailChange = { vm.regEmail = it },
                password = vm.regPassword,
                onPasswordChange = { vm.regPassword = it },
                confirmPassword = vm.regConfirmPassword,
                onConfirmPasswordChange = { vm.regConfirmPassword = it },
                onRegisterClick = { vm.register() },
                onLoginClick = { vm.switchMode(false) },
                isLoading = state.isLoading,
                mensajeError = state.mensajeError,
                mensajeExito = state.mensajeExito
            )
        } else {
            VeterinaryCard(
                email = vm.email,
                onEmailChange = { vm.email = it },
                password = vm.password,
                onPasswordChange = { vm.password = it },
                onLoginClick = { vm.login() },
                onRegisterClick = { vm.switchMode(true) },
                isLoading = state.isLoading,
                mensajeError = state.mensajeError
            )
        }
    }
}