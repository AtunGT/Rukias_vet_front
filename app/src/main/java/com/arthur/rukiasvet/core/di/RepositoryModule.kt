package com.arthur.rukiasvet.core.di

import android.content.Context
import com.arthur.rukiasvet.core.database.dao.BranchDao
import com.arthur.rukiasvet.core.database.dao.PatientDao
import com.arthur.rukiasvet.core.database.dao.ProductDao
import com.arthur.rukiasvet.core.hardware.camera.CameraRepository
import com.arthur.rukiasvet.core.hardware.camera.CameraRepositoryImpl
import com.arthur.rukiasvet.core.hardware.location.LocationRepository
import com.arthur.rukiasvet.core.hardware.location.LocationRepositoryImpl
import com.arthur.rukiasvet.core.hardware.storage.FileRepository
import com.arthur.rukiasvet.core.hardware.storage.FileRepositoryImpl
import com.arthur.rukiasvet.core.navigation.FeatureNavGraph
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.core.session.SessionManager
import com.arthur.rukiasvet.core.session.SessionRepository
import com.arthur.rukiasvet.core.utils.TokenDeco
import com.arthur.rukiasvet.features.branch.data.repositories.BranchRepositoryImpl
import com.arthur.rukiasvet.features.branch.domain.repositories.BranchRepository
import com.arthur.rukiasvet.features.branch.navigation.BranchNavGraph
import com.arthur.rukiasvet.features.login.data.repositories.VeterinaryRepositoryImpl
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.features.login.navigation.AuthNavGraph
import com.arthur.rukiasvet.features.patient.data.repositories.PatientRepositoryImpl
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import com.arthur.rukiasvet.features.product.data.repositories.ProductRepositoryImpl
import com.arthur.rukiasvet.features.product.domain.repositories.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides @Singleton
    fun provideVeterinaryRepository(
        api: Api_Veterinaria,
        tokenDeco: TokenDeco
    ): VeterinaryRepository = VeterinaryRepositoryImpl(api, tokenDeco)

    @Provides @Singleton
    fun provideBranchRepository(
        api: Api_Veterinaria,
        branchDao: BranchDao
    ): BranchRepository = BranchRepositoryImpl(api, branchDao)

    @Provides @Singleton
    fun providePatientRepository(
        api: Api_Veterinaria,
        patientDao: PatientDao
    ): PatientRepository = PatientRepositoryImpl(api, patientDao)

    @Provides @Singleton
    fun provideProductRepository(
        api: Api_Veterinaria,
        productDao: ProductDao
    ): ProductRepository = ProductRepositoryImpl(api, productDao)

    @Provides @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context
    ): LocationRepository = LocationRepositoryImpl(context)

    @Provides @Singleton
    fun provideFileRepository(): FileRepository = FileRepositoryImpl()

    @Provides @Singleton
    fun provideCameraRepository(
        fileRepository: FileRepository
    ): CameraRepository = CameraRepositoryImpl()

    @Provides
    @Singleton
    fun provideFeatureNavGraphs(
        authNavGraph: AuthNavGraph,
        branchNavGraph: BranchNavGraph
    ): List<@JvmSuppressWildcards FeatureNavGraph> = listOf(
        authNavGraph,
        branchNavGraph
    )
}