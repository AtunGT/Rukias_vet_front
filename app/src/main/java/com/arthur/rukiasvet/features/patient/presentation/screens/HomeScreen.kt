package com.arthur.rukiasvet.features.patient.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel

@Composable
fun HomeScreen(
    nombreUsuario: String,
    token: String,
    patientVm: PatientViewModel,
    onCerrarSesion: () -> Unit,
    onAddPatientClick: () -> Unit
) {
    val BluePrimary = Color(0xFF1E60F6)
    val BackgroundColor = Color(0xFFEFF6FF)
    val TextDark = Color(0xFF1F2937)
    val TextGray = Color(0xFF6B7280)

    val state by patientVm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        patientVm.cargarPacientes(token)
    }

    Scaffold(
        containerColor = BackgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPatientClick,
                containerColor = BluePrimary,
                shape = CircleShape,
                modifier = Modifier.size(60.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(45.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = BluePrimary
                    ) {
                        Box(contentAlignment = Alignment.Center) { Text("🐾", fontSize = 24.sp) }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Clínica Veterinaria", color = TextDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Hola, $nombreUsuario", color = TextGray, fontSize = 14.sp)
                    }
                    IconButton(onClick = onCerrarSesion) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = TextGray)
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Buscar paciente...", color = TextGray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${state.listaPacientes.size} pacientes registrados",
                        color = BluePrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BluePrimary)
                    }
                } else if (state.listaPacientes.isEmpty()) {
                    EmptyState()
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.listaPacientes) { paciente ->
                            PatientItem(paciente)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PatientItem(paciente: Patient) { // <--- RECIBE PATIENT (Dominio)
    // Ya no necesitamos los "?:" porque el Mapper en la capa de datos ya limpió los nulos.
    // Simplemente accedemos a las propiedades.

    val nombre = paciente.name
    val dueno = paciente.owner
    val descripcion = paciente.description
    val telefono = paciente.telephone
    val edad = paciente.age

    // Tomamos la inicial
    val letraInicial = if (nombre.isNotEmpty()) nombre.take(1).uppercase() else "?"

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con inicial
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = Color(0xFFE0E7FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = letraInicial,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E60F6)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Datos del Paciente
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                // Dueño y Teléfono
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dueño: $dueno", color = Color.Gray, fontSize = 13.sp)
                    if (telefono.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = " $telefono", color = Color.Gray, fontSize = 13.sp)
                    }
                }

                // Edad (si existe)
                if (edad.isNotEmpty() && edad != "0") {
                    Text(text = "Edad: $edad", color = Color.Gray, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Descripción
                if (descripcion.isNotEmpty()) {
                    Text(
                        text = descripcion,
                        color = Color(0xFF6B7280),
                        fontSize = 12.sp,
                        maxLines = 2,
                        lineHeight = 14.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyState() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🐾", fontSize = 50.sp, color = Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(16.dp))
            Text("No hay pacientes registrados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Comienza agregando tu primer paciente", color = Color.Gray, fontSize = 14.sp)
        }
    }
}