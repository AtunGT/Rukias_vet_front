package com.arthur.rukiasvet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arthur.rukiasvet.core.database.dao.BranchDao
import com.arthur.rukiasvet.core.database.dao.PatientDao
import com.arthur.rukiasvet.core.database.dao.ProductDao
import com.arthur.rukiasvet.core.database.entities.BranchEntity
import com.arthur.rukiasvet.core.database.entities.PatientEntity
import com.arthur.rukiasvet.core.database.entities.ProductEntity

@Database(
    entities = [BranchEntity::class, PatientEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun branchDao(): BranchDao
    abstract fun patientDao(): PatientDao
    abstract fun productDao(): ProductDao
}