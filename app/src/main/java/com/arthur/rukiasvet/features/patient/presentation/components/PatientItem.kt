package com.arthur.rukiasvet.features.patient.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthur.rukiasvet.features.patient.domain.model.Patient

@Composable
fun PatientItem(
    paciente: Patient,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabecera: Nombre y Acciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = paciente.name.lowercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${paciente.weight} kg • ${paciente.age} años",
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                }
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, "Editar", tint = Color(0xFF2563EB))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = Color(0xFFDC2626))
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            PatientInfoRow(Icons.Default.Transgender, "Género", paciente.gender, iconTint = Color.Black)
            PatientInfoRow(Icons.Default.Pets, "Especie", paciente.species)
            PatientInfoRow(Icons.Default.Person, "Dueño", paciente.owner)
            PatientInfoRow(
                icon = Icons.Default.Phone,
                label = "Tel",
                value = paciente.telephone,
                valueColor = Color(0xFF2563EB)
            )

            if (paciente.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Descripción: ${paciente.description}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}