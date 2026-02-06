package com.arthur.rukiasvet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arthur.rukiasvet.core.di.AppContainer
import com.arthur.rukiasvet.core.ui.theme.AppTheme
import com.arthur.rukiasvet.features.login.di.VeterinaryModule
import com.arthur.rukiasvet.features.login.presentation.screens.VeterinaryScreen
import com.arthur.rukiasvet.features.patient.di.PatientModule

class MainActivity : ComponentActivity() {

    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(this)
        val loginModule = VeterinaryModule(appContainer)
        val patientModule = PatientModule(appContainer)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    VeterinaryScreen(
                        loginFactory = loginModule.provideViewModelFactory(),
                        patientFactory = patientModule.provideViewModelFactory()
                    )
                }
            }
        }
    }
}