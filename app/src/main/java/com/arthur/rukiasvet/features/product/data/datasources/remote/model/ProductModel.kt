package com.arthur.rukiasvet.features.product.data.model

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    @SerializedName("idproduct")   val id: Int? = null,
    @SerializedName("idbranch")    val branchId: Int,
    @SerializedName("name")        val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price")       val price: Double,
    @SerializedName("stock")       val stock: Int,
    @SerializedName("type")        val category: String,
    @SerializedName("imageurl")    val imageUrl: String = ""
)

data class ProductResponse(
    @SerializedName("IDProduct")   val id: Int,
    @SerializedName("Name")        val name: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Price")       val price: Double,
    @SerializedName("Stock")       val stock: Int,
    @SerializedName("Type")        val category: String = "",
    @SerializedName("BranchID")    val branchId: Int,
    @SerializedName("ImageURL")    val imageUrl: String = ""
)