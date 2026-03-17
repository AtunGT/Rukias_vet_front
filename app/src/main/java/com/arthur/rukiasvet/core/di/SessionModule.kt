package com.arthur.rukiasvet.core.di

import com.arthur.rukiasvet.core.session.SessionManager
import com.arthur.rukiasvet.core.session.SessionManagerImpl
import com.arthur.rukiasvet.core.session.SessionRepository
import com.arthur.rukiasvet.core.session.SessionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionManager(
        impl: SessionManagerImpl
    ): SessionManager

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}