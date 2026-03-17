package com.arthur.rukiasvet.core.di

import android.content.Context
import androidx.room.Room
import com.arthur.rukiasvet.core.database.AppDatabase
import com.arthur.rukiasvet.core.database.dao.BranchDao
import com.arthur.rukiasvet.core.database.dao.PatientDao
import com.arthur.rukiasvet.core.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "rukiasvet.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideBranchDao(db: AppDatabase): BranchDao = db.branchDao()
    @Provides fun providePatientDao(db: AppDatabase): PatientDao = db.patientDao()
    @Provides fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()
}