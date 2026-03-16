package com.arthur.rukiasvet.core.di

import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.core.utils.TokenDeco // ← corregido
import com.arthur.rukiasvet.features.login.data.repositories.VeterinaryRepositoryImpl
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.features.patient.data.repositories.PatientRepositoryImpl
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideVeterinaryRepository(
        api: Api_Veterinaria,
        tokenDeco: TokenDeco
    ): VeterinaryRepository {
        return VeterinaryRepositoryImpl(api, tokenDeco)
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        api: Api_Veterinaria
    ): PatientRepository {
        return PatientRepositoryImpl(api)
    }
}