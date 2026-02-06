package com.arthur.rukiasvet.features.patient.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
fun PatientForm(
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
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                IconButton(onClick = onCerrarClick) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.Black)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            PatientTextField(nombre, onNombreChange, "Nombre de la Mascota *")
            Spacer(modifier = Modifier.height(12.dp))

            PatientTextField(peso, onPesoChange, "Peso (kg) *")
            Spacer(modifier = Modifier.height(12.dp))

            PatientTextField(edad, onEdadChange, "Edad (años) *")
            Spacer(modifier = Modifier.height(12.dp))


            DropdownField(
                label = "Género *",
                value = genero,
                options = listOf("Macho", "Hembra"),
                onValueChange = onGeneroChange,
            )

            Spacer(modifier = Modifier.height(12.dp))

            DropdownField(
                label = "Especie *",
                value = especie,
                options = listOf("Perro", "Gato", "Roedor", "Reptil", "Ave"),
                onValueChange = onEspecieChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            PatientTextField(dueno, onDuenoChange, "Nombre del Dueño *")
            Spacer(modifier = Modifier.height(12.dp))

            PatientTextField(telefono, onTelefonoChange, "Teléfono *")
            Spacer(modifier = Modifier.height(12.dp))

            PatientTextField(
                value = descripcion,
                onValueChange = onDescripcionChange,
                label = "Descripción",
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            if (mensajeError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensajeError, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormActions(
                onCancelar = onCerrarClick,
                onGuardar = onGuardarClick,
                isLoading = isLoading
            )
        }
    }
}
