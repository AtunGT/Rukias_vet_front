package com.arthur.rukiasvet.features.patient.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PatientCard(
    nombre: String,
    onNombreChange: (String) -> Unit,
    peso: String,
    onPesoChange: (String) -> Unit,
    edad: String,
    onEdadChange: (String) -> Unit,
    genero: String,
    onGeneroChange: (String) -> Unit,
    especie: String,
    onEspecieChange: (String) -> Unit,
    dueno: String,
    onDuenoChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    onGuardarClick: () -> Unit,
    onCerrarClick: () -> Unit,
    isLoading: Boolean,
    mensajeError: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nuevo Paciente",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onCerrarClick) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text("Nombre de la Mascota *") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = peso,
                onValueChange = onPesoChange,
                label = { Text("Peso (kg) *") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = edad,
                onValueChange = onEdadChange,
                label = { Text("Edad (años) *") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            DropdownField(
                label = "Género *",
                value = genero,
                options = listOf("Macho", "Hembra"),
                onValueChange = onGeneroChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            DropdownField(
                label = "Especie *",
                value = especie,
                options = listOf("Perro", "Gato", "Roedor", "Reptil", "Ave"),
                onValueChange = onEspecieChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dueno,
                onValueChange = onDuenoChange,
                label = { Text("Nombre del Dueño *") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = onTelefonoChange,
                label = { Text("Teléfono *") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensajeError, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                OutlinedButton(
                    onClick = onCerrarClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onGuardarClick,
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Agregar")
                    }
                }
            }
        }
    }
}
