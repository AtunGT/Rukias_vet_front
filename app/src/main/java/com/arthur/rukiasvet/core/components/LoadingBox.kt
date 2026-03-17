package com.arthur.rukiasvet.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier.Companion.fillMaxSize(),
    color: Color = Color(0xFF1E60F6)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Companion.Center
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 4.dp
        )
    }
}
