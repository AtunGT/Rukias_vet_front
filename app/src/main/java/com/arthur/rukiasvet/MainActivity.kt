package com.arthur.rukiasvet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arthur.rukiasvet.core.navigation.FeatureNavGraph
import com.arthur.rukiasvet.core.navigation.NavigationWrapper
import com.arthur.rukiasvet.core.session.SessionManager
import com.arthur.rukiasvet.core.ui.theme.AppTheme
import com.arthur.rukiasvet.features.login.presentation.screens.VeterinaryScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    @Inject lateinit var featureNavGraphs: List<@JvmSuppressWildcards FeatureNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                NavigationWrapper(
                    sessionManager = sessionManager,
                    featureNavGraphs = featureNavGraphs
                )
            }
        }
    }
}