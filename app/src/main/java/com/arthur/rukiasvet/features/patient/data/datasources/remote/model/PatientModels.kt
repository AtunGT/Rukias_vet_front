package com.arthur.rukiasvet.features.patient.data.model

import com.google.gson.annotations.SerializedName

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
    // Usamos @SerializedName para asegurar que coincida con el JSON EXACTO
    @SerializedName("_id") val _id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("owner") val owner: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("date") val date: String? = null,

    @SerializedName("species") val species: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("weight") val weight: Double? = null, // Puede ser Double o Int
    @SerializedName("age") val age: String? = null,
    @SerializedName("telephone") val telephone: String? = null
)