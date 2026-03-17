package com.arthur.rukiasvet.core.database.dao

import androidx.room.*
import com.arthur.rukiasvet.core.database.entities.PatientEntity

@Dao
interface PatientDao {
    @Query("SELECT * FROM patients WHERE branchId = :branchId")
    suspend fun getByBranch(branchId: Int): List<PatientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patients: List<PatientEntity>)

    @Query("DELETE FROM patients WHERE branchId = :branchId")
    suspend fun deleteByBranch(branchId: Int)
}