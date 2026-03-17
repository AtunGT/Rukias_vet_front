package com.arthur.rukiasvet.core.hardware.location

import com.arthur.rukiasvet.core.hardware.location.model.Location


interface LocationRepository {
    suspend fun getCurrentLocation(): Result<Location>
    suspend fun getAddressFromLocation(lat: Double, lng: Double): String  // ← añade
}