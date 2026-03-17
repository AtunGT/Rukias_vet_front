package com.arthur.rukiasvet.features.branch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arthur.rukiasvet.features.branch.domain.model.Branch

@Composable
fun BranchList(
    branches: List<Branch>,
    onBranchClick: (Int) -> Unit,
    onEdit: (Branch) -> Unit,
    onDelete: (Branch) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(branches) { branch ->
            BranchItem(
                branch = branch,
                onClick = { onBranchClick(branch.id) },
                onEdit = { onEdit(branch) },
                onDelete = { onDelete(branch) }
            )
        }
    }
}