package com.arthur.rukiasvet.features.branch.data.model

import com.google.gson.annotations.SerializedName

data class BranchRequest(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String
)
data class BranchResponse(
    @SerializedName("IDBranch") val id: Int = 0,
    @SerializedName("Name") val name: String? = null,
    @SerializedName("Address") val address: String? = null,
    @SerializedName("UserID") val userId: Int = 0
)