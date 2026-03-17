package com.arthur.rukiasvet.features.login.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.arthur.rukiasvet.core.navigation.Branches
import com.arthur.rukiasvet.core.navigation.FeatureNavGraph
import com.arthur.rukiasvet.core.navigation.Login
import com.arthur.rukiasvet.features.login.presentation.screens.LoginOrRegisterContent
import com.arthur.rukiasvet.features.login.presentation.viewmodels.VeterinaryViewModel
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class AuthNavGraph @Inject constructor() : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Login> {
            val vm: VeterinaryViewModel = hiltViewModel()
            val state by vm.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(state.isLoggedIn) {
                if (state.isLoggedIn) {
                    navController.navigate(Branches) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            }

            LoginOrRegisterContent(vm = vm, state = state)
        }
    }
}