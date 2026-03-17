package com.arthur.rukiasvet.features.branch.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.arthur.rukiasvet.core.navigation.BranchDetail
import com.arthur.rukiasvet.core.navigation.Branches
import com.arthur.rukiasvet.core.navigation.FeatureNavGraph
import com.arthur.rukiasvet.core.navigation.PatientForm
import com.arthur.rukiasvet.core.session.SessionManager
import com.arthur.rukiasvet.features.branch.presentation.screens.BranchDetailScreen
import com.arthur.rukiasvet.features.branch.presentation.screens.BranchListScreen
import com.arthur.rukiasvet.features.patient.presentation.screens.PatientFormScreen
import com.arthur.rukiasvet.features.branch.presentation.viewmodels.BranchViewModel
import com.arthur.rukiasvet.features.patient.presentation.viewmodels.PatientViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class BranchNavGraph @Inject constructor(
    private val sessionManager: SessionManager
) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Branches> {
            val branchViewModel: BranchViewModel = hiltViewModel()
            val state by branchViewModel.uiState.collectAsStateWithLifecycle()
            val isAuthenticated by branchViewModel.isAuthenticated.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                if (branchViewModel.token.value != null) {
                    branchViewModel.loadBranches()
                }
            }

            LaunchedEffect(isAuthenticated) {
                if (isAuthenticated) {
                    branchViewModel.loadBranches()
                }
            }

            BranchListScreen(
                state = state,
                onBranchClick = { branchId ->
                    navController.navigate(BranchDetail(branchId))
                },
                onAddBranchClick = {},
                onDeleteBranch = { branch ->
                    branchViewModel.deleteBranch(branch)
                },
                onEditBranch = { branch ->
                    branchViewModel.startEdit(branch)
                }
            )
        }


        navGraphBuilder.composable<BranchDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<BranchDetail>()
            val branchId = args.branchId

            val patientViewModel: PatientViewModel = hiltViewModel()
            val patientState by patientViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(branchId) {
                patientViewModel.loadPatientsByBranch(branchId)
            }

            BranchDetailScreen(
                branchId = branchId,
                patientState = patientState,
                onAddPatientClick = {
                    navController.navigate(PatientForm(branchId = branchId, patientId = null))
                },
                onEditPatient = { patient ->
                    navController.navigate(PatientForm(branchId = branchId, patientId = patient.id))
                },
                onDeletePatient = { patient ->
                    patientViewModel.deletePatient(patient, branchId)
                },
                onBack = { navController.popBackStack() }
            )
        }


        navGraphBuilder.composable<PatientForm> { backStackEntry ->
            val args = backStackEntry.toRoute<PatientForm>()

            val stableUserId = remember {
                mutableStateOf(sessionManager.userId.value)
            }

            val userId by sessionManager.userId.collectAsStateWithLifecycle()
            LaunchedEffect(userId) {
                if (userId != null) stableUserId.value = userId
            }

            stableUserId.value?.let { id ->
                PatientFormScreen(
                    branchId = args.branchId,
                    patientId = args.patientId,
                    userId = id,
                    onSaveSuccess = { navController.popBackStack() },
                    onCancel = { navController.popBackStack() }
                )
            } ?: run {
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
    }
}