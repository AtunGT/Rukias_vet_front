package com.arthur.rukiasvet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.arthur.rukiasvet.core.session.SessionManager

@Composable
fun NavigationWrapper(
    sessionManager: SessionManager,
    featureNavGraphs: List<FeatureNavGraph>
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        featureNavGraphs.forEach { graph ->
            graph.registerGraph(this, navController)
        }
    }
}