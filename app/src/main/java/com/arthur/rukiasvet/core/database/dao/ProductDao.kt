package com.arthur.rukiasvet.core.database.dao

import androidx.room.*
import com.arthur.rukiasvet.core.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE branchId = :branchId")
    suspend fun getByBranch(branchId: Int): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE branchId = :branchId")
    suspend fun deleteByBranch(branchId: Int)
}