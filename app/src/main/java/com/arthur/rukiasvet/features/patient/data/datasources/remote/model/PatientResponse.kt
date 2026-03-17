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

data class PatientResponse(
    @SerializedName("IdPatient") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Species") val species: String,
    @SerializedName("Gender") val gender: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Weight") val weight: Double,
    @SerializedName("Age") val age: String,
    @SerializedName("Owner") val owner: String,
    @SerializedName("Telephone") val telephone: String,
    @SerializedName("BranchID") val branchId: Int,
    @SerializedName("ImageURL") val imageUrl: String = ""
)