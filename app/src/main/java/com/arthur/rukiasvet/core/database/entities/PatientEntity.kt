package com.arthur.rukiasvet.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val species: String,
    val description: String,
    val gender: String,
    val weight: Double,
    val age: String,
    val owner: String,
    val telephone: String,
    val branchId: Int,
    val imageUrl: String
)