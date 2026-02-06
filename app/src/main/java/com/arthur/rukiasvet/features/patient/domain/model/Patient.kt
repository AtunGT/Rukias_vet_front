package com.arthur.rukiasvet.features.patient.domain.model

data class Patient(
    val id: Int,
    val name: String,
    val species: String,
    val description: String,
    val gender: String,
    val weight: Double,
    val age: String,
    val owner: String,
    val telephone: String
)