package com.arthur.rukiasvet.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "branches")
data class BranchEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val address: String
)