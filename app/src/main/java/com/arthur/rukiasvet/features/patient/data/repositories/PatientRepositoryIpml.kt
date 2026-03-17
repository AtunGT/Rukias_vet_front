package com.arthur.rukiasvet.features.patient.data.repositories

import android.util.Log
import com.arthur.rukiasvet.core.network.Api_Veterinaria
import com.arthur.rukiasvet.features.patient.data.datasources.remote.mapper.toDomain
import com.arthur.rukiasvet.features.patient.data.model.PatientRequest
import com.arthur.rukiasvet.features.patient.domain.model.Patient
import com.arthur.rukiasvet.features.patient.domain.repositories.PatientRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: Api_Veterinaria
) : PatientRepository {

    override suspend fun addPatient(
        token: String,
        patient: PatientRequest,
        imageFile: File?
    ): Boolean {
        return try {
            val namePart = patient.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val speciesPart = patient.species.toRequestBody("text/plain".toMediaTypeOrNull())
            val genderPart = patient.gender.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionPart = patient.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val weightPart = patient.weight.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val agePart = patient.age.toRequestBody("text/plain".toMediaTypeOrNull())
            val ownerPart = patient.owner.toRequestBody("text/plain".toMediaTypeOrNull())
            val telephonePart = patient.telephone.toRequestBody("text/plain".toMediaTypeOrNull())
            val branchIdPart = patient.branchId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageFile?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            val response = api.addPatient(
                token = "Bearer $token",
                name = namePart,
                species = speciesPart,
                gender = genderPart,
                description = descriptionPart,
                weight = weightPart,
                age = agePart,
                owner = ownerPart,
                telephone = telephonePart,
                branchId = branchIdPart,
                image = imagePart
            )
            Log.d("PatientRepo", "Response code: ${response.code()}")
            Log.d("PatientRepo", "Response body: ${response.errorBody()?.string()}")
            Log.d("PatientRepo", "isSuccessful: ${response.isSuccessful}")

            response.isSuccessful
        } catch (e: Exception) {
            Log.e("PatientRepo", "Error: ${e.message}")
            Log.e("PatientRepo", "Cause: ${e.cause}")
            false
        }
    }

    override suspend fun updatePatient(
        token: String,
        id: Int,
        patient: PatientRequest,
        imageFile: File?
    ): Boolean {
        return try {
            val namePart = patient.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val speciesPart = patient.species.toRequestBody("text/plain".toMediaTypeOrNull())
            val genderPart = patient.gender.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionPart = patient.description.toRequestBody("text/plain".toMediaTypeOrNull())
            val weightPart = patient.weight.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val agePart = patient.age.toRequestBody("text/plain".toMediaTypeOrNull())
            val ownerPart = patient.owner.toRequestBody("text/plain".toMediaTypeOrNull())
            val telephonePart = patient.telephone.toRequestBody("text/plain".toMediaTypeOrNull())
            val branchIdPart = patient.branchId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagePart = imageFile?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            val response = api.updatePatient(
                token = "Bearer $token",
                id = id,
                name = namePart,
                species = speciesPart,
                gender = genderPart,
                description = descriptionPart,
                weight = weightPart,
                age = agePart,
                owner = ownerPart,
                telephone = telephonePart,
                branchId = branchIdPart,
                image = imagePart
            )
            Log.d("PatientRepo", "Update code: ${response.code()}")
            Log.d("PatientRepo", "Update error: ${response.errorBody()?.string()}")
            response.isSuccessful        } catch (e: Exception) {
            Log.e("PatientRepo", "Error updating patient", e)
            false
        }
    }

    override suspend fun getPatients(token: String): List<Patient> {
        return try {
            val response = api.getPatients("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPatientsByBranch(token: String, branchId: Int): List<Patient> {
        return try {
            val response = api.getPatientsByBranch("Bearer $token", branchId)
            if (response.isSuccessful) {
                response.body()?.toDomain() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deletePatient(token: String, id: Int): Boolean {
        return try {
            val response = api.deletePatient("Bearer $token", id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getPatientById(token: String, id: Int): Patient? {
        return try {
            val response = api.getPatientById("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}