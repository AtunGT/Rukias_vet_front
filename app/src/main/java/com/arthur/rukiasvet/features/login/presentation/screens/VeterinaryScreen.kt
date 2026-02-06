package com.arthur.rukiasvet.features.login.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arthur.rukiasvet.features.login.presentation.components.RegisterCard
import com.arthur.rukiasvet.features.login.presentation.components.VeterinaryCard
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryUIState
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryViewModel
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryViewModelFactory
import com.arthur.rukiasvet.features.patient.presentation.screens.HomeScreen
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientScreen
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModelFactory

@Composable
fun VeterinaryScreen(
    loginFactory: VeterinaryViewModelFactory,
    patientFactory: PatientViewModelFactory
) {
    val loginVm: VeterinaryViewModel = viewModel(factory = loginFactory)
    val state by loginVm.uiState.collectAsStateWithLifecycle()
    val patientVm: PatientViewModel = viewModel(factory = patientFactory)

    var showAddPatient by remember { mutableStateOf(false) }

    if (state.isLoggedIn) {
        HomeScreen(
            nombreUsuario = state.nombreUsuario,
            token = state.diagnosticoReal,
            patientVm = patientVm,
            onCerrarSesion = { loginVm.cerrarSesion() },
            onAddPatientClick = { showAddPatient = true }
        )

        if (showAddPatient) {
            Dialog(
                onDismissRequest = { showAddPatient = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                PatientScreen(
                    vm = patientVm,
                    token = state.diagnosticoReal,
                    onClose = { showAddPatient = false }
                )
            }
        }
    } else {
        LoginOrRegisterContent(vm = loginVm, state = state)
    }
}


@Composable
fun LoginOrRegisterContent(vm: VeterinaryViewModel, state: VeterinaryUIState) {
    val BluePrimary = Color(0xFF1E60F6)
    val BackgroundColor = Color(0xFFEFF6FF)

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().background(BluePrimary))

        if (state.esRegistro) {
            RegisterCard(
                nombre = vm.regNombre,
                onNombreChange = { vm.regNombre = it },
                apellidos = vm.regApellidos,
                onApellidosChange = { vm.regApellidos = it },
                email = vm.regEmail,
                onEmailChange = { vm.regEmail = it },
                password = vm.regPassword,
                onPasswordChange = { vm.regPassword = it },
                confirmPassword = vm.regConfirmPassword,
                onConfirmPasswordChange = { vm.regConfirmPassword = it },
                onRegisterClick = { vm.registrar() },
                onLoginClick = { vm.cambiarModo(registro = false) },
                isLoading = state.isLoading,
                mensajeError = state.mensajeError,
                mensajeExito = state.mensajeExito
            )
        } else {
            VeterinaryCard(
                email = vm.usuario,
                onEmailChange = { vm.usuario = it },
                password = vm.contrasena,
                onPasswordChange = { vm.contrasena = it },
                onLoginClick = { vm.iniciarSesion() },
                onRegisterClick = { vm.cambiarModo(registro = true) },
                isLoading = state.isLoading,
                mensajeError = state.mensajeError
            )
        }
    }
}