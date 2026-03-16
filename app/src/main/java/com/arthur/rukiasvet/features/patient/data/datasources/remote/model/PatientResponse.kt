package com.arthur.rukiasvet.features.patient.data.model

import com.google.gson.annotations.SerializedName

data class PatientRequest(
    @SerializedName("idpatients") val id: Int? = null,
    @SerializedName("idbranch") val branchId: Int,
    @SerializedName("iduser") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("description") val description: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("age") val age: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("imageurl") val imageUrl: String
)