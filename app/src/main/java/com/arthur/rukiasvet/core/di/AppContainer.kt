package com.arthur.rukiasvet.core.di

import android.content.Context
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.core.utils.Tokendeco
import com.arthur.rukiasvet.features.login.data.repositories.VeterinaryRepositoryImpl
import com.arthur.rukiasvet.features.login.domain.repositories.VeterinaryRepository
import com.arthur.rukiasvet.features.patient.data.repositories.PatientRepositoryImpl
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rukiasapi.viewdns.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api_Veterinaria = retrofit.create(Api_Veterinaria::class.java)

    val tokendeco: Tokendeco by lazy {
        Tokendeco()
    }

    val veterinaryRepository: VeterinaryRepository by lazy {
        VeterinaryRepositoryImpl(api, tokendeco)
    }

    val patientRepository: PatientRepository by lazy {
        PatientRepositoryImpl(api)
    }
}