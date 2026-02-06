package com.arthur.rukiasvet.features.patient.data.model

import com.google.gson.annotations.SerializedName

data class PatientRequest(
    @SerializedName("IdPatient") val id: Int? = null,

    @SerializedName("Name") val name: String,
    @SerializedName("Species") val species: String,
    @SerializedName("Gender") val gender: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Weight") val weight: Double,
    @SerializedName("Age") val age: String,
    @SerializedName("Owner") val owner: String,
    @SerializedName("Telephone") val telephone: String
)