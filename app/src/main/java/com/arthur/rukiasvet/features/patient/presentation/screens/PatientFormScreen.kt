package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.arthur.rukiasvet.core.components.CustomTextField
import com.arthur.rukiasvet.core.hardware.camera.CameraViewModel
import com.arthur.rukiasvet.core.hardware.camera.rememberCameraManager
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientFormScreen(
    branchId: Int,
    patientId: Int?,
    userId: Int,
    onSaveSuccess: () -> Unit,
    onCancel: () -> Unit,
    viewModel: PatientViewModel = hiltViewModel(),
    cameraVm: CameraViewModel = hiltViewModel()
) {
    val capturedImageFile by viewModel.capturedImageFile.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val cameraManager = rememberCameraManager(
        cameraRepository = cameraVm.cameraRepository,
        fileRepository = cameraVm.fileRepository,
        onImageReady = { file -> viewModel.setCapturedImage(file) }
    )
    LaunchedEffect(patientId) {
        patientId?.let { viewModel.loadPatientForEdit(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (viewModel.editingPatientId == null) "Nuevo paciente"
                        else "Editar paciente"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                value = state.nombre,
                onValueChange = viewModel::onNameChange,
                label = "Nombre",
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.especie,
                onValueChange = { viewModel.onSpeciesChange(it) },
                label = "Especie",
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            var expandedGender by remember { mutableStateOf(false) }
            val generos = listOf("Macho", "Hembra")

            ExposedDropdownMenuBox(
                expanded = expandedGender,
                onExpandedChange = { expandedGender = !expandedGender }
            ) {
                OutlinedTextField(
                    value = state.genero,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Género") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    enabled = !state.isLoading
                )
                ExposedDropdownMenu(
                    expanded = expandedGender,
                    onDismissRequest = { expandedGender = false }
                ) {
                    generos.forEach { genero ->
                        DropdownMenuItem(
                            text = { Text(genero) },
                            onClick = {
                                viewModel.onGenderChange(genero)
                                expandedGender = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.peso,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) viewModel.onWeightChange(it) },
                label = "Peso (kg)",
                enabled = !state.isLoading,
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.edad,
                onValueChange = viewModel::onAgeChange,
                label = "Edad",
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.dueno,
                onValueChange = viewModel::onOwnerChange,
                label = "Dueño",
                enabled = !state.isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.telefono,
                onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.onPhoneChange(it) },
                label = "Teléfono",
                enabled = !state.isLoading,
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = state.descripcion,
                onValueChange = viewModel::onDescriptionChange,
                label = "Descripción",
                enabled = !state.isLoading,
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Foto del paciente", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    val existingImageUrl = state.listaPacientes
                        .firstOrNull { it.id == viewModel.editingPatientId }
                        ?.imageUrl
                        ?.takeIf { it.isNotEmpty() }

                    when {
                        capturedImageFile != null -> {
                            AsyncImage(
                                model = capturedImageFile,
                                contentDescription = "Vista previa",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        existingImageUrl != null -> {
                            AsyncImage(
                                model = existingImageUrl,
                                contentDescription = "Foto actual",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Sin imagen", color = Color.Gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = cameraManager.onTakePicture,
                            enabled = !state.isLoading
                        ) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cámara")
                        }
                        Button(
                            onClick = cameraManager.onPickFromGallery,
                            enabled = !state.isLoading
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Galería")
                        }
                    }

                    if (capturedImageFile != null) {
                        TextButton(
                            onClick = {
                                cameraManager.onClear()
                                viewModel.clearImage()
                            },
                            enabled = !state.isLoading
                        ) {
                            Text("Quitar imagen")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.savePatient(
                        branchId = branchId,
                        userId = userId,
                        onSuccess = onSaveSuccess
                    )
                },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        if (viewModel.editingPatientId == null) "Guardar"
                        else "Actualizar"
                    )
                }
            }

            if (state.mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}