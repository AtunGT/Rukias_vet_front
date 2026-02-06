package com.arthur.rukiasvet.features.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterCard(
    nombre: String,
    onNombreChange: (String) -> Unit,
    apellidos: String,
    onApellidosChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    mensajeError: String,
    mensajeExito: String
) {
    val BluePrimary = Color(0xFF1E60F6)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            RegisterHeader(BluePrimary)

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                AuthTextField(nombre, onNombreChange, "Nombre")
                Spacer(modifier = Modifier.height(8.dp))

                AuthTextField(apellidos, onApellidosChange, "Apellidos")
                Spacer(modifier = Modifier.height(8.dp))

                AuthTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = "Correo Electrónico",
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(8.dp))

                AuthTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                AuthTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = "Confirmar Contraseña",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Registrarse", fontSize = 16.sp, color = Color.White)
                    }
                }

                if (mensajeError.isNotEmpty()) {
                    Text(mensajeError, color = Color.Red, modifier = Modifier.padding(top = 12.dp))
                }
                if (mensajeExito.isNotEmpty()) {
                    Text(mensajeExito, color = Color(0xFF4CAF50), modifier = Modifier.padding(top = 12.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onLoginClick) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = BluePrimary)
                }
            }
        }
    }
}