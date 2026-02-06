package com.arthur.rukiasvet.features.patient.data.model

data class PatientRequest(
    val name: String,
    val species: String,
    val race: String = "No especificada",
    val description: String,
    val gender: String,
    val weight: Double,
    val age: String,
    val owner: String,
    val telephone: String
)

data class PatientResponse(
    val _id: String,
    val name: String,
    val owner: String,
    val description: String,
    val date: String? = null
)