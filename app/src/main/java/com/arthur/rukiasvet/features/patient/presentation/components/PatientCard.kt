package com.arthur.rukiasvet.features.patient.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PatientCard(
    nombre: String, onNombreChange: (String) -> Unit,
    peso: String, onPesoChange: (String) -> Unit,
    edad: String, onEdadChange: (String) -> Unit,
    dueno: String, onDuenoChange: (String) -> Unit,
    telefono: String, onTelefonoChange: (String) -> Unit,
    descripcion: String, onDescripcionChange: (String) -> Unit,
    onGuardarClick: () -> Unit,
    onCerrarClick: () -> Unit,
    isLoading: Boolean,
    mensajeError: String
) {
    val BluePrimary = Color(0xFF1E60F6)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Gray)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE5E7EB))
        Spacer(modifier = Modifier.height(16.dp))


        InputItem("Nombre de la Mascota *", nombre, onNombreChange, KeyboardType.Text)
        Spacer(modifier = Modifier.height(16.dp))

        InputItem("Peso (kg) *", peso, onPesoChange, KeyboardType.Number)
        Spacer(modifier = Modifier.height(16.dp))

        InputItem("Edad (años) *", edad, onEdadChange, KeyboardType.Number)
        Spacer(modifier = Modifier.height(16.dp))

        InputItem("Nombre del Dueño *", dueno, onDuenoChange, KeyboardType.Text)
        Spacer(modifier = Modifier.height(16.dp))

        InputItem("Teléfono *", telefono, onTelefonoChange, KeyboardType.Phone)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Descripción",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            maxLines = 5
        )


        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(mensajeError, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = onCerrarClick,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancelar", color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onGuardarClick,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Agregar")
                }
            }
        }
    }
}

@Composable
fun InputItem(label: String, value: String, onChange: (String) -> Unit, keyboard: KeyboardType) {
    Text(
        text = label,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF374151),
        modifier = Modifier.padding(bottom = 4.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        singleLine = true
    )
}