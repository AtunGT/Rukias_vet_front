package com.arthur.rukiasvet.features.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterHeader(backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(modifier = Modifier.size(48.dp), shape = CircleShape, color = Color.White) {
                Box(contentAlignment = Alignment.Center) { Text("🐾", fontSize = 22.sp) }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Rukia's Vet", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Sistema de Gestión", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
        }
    }
}