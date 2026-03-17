package com.arthur.rukiasvet.core.database.dao

import androidx.room.*
import com.arthur.rukiasvet.core.database.entities.BranchEntity

@Dao
interface BranchDao {
    @Query("SELECT * FROM branches")
    suspend fun getAll(): List<BranchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(branches: List<BranchEntity>)

    @Query("DELETE FROM branches")
    suspend fun deleteAll()
}