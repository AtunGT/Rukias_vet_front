package com.arthur.rukiasvet.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val branchId: Int,
    val imageUrl: String
)