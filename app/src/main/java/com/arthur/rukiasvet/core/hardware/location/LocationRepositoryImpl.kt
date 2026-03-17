package com.arthur.rukiasvet.core.hardware.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.annotation.RequiresPermission
import com.arthur.rukiasvet.core.hardware.location.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val context: Context
) : LocationRepository {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    override suspend fun getCurrentLocation(): Result<Location> {
        return if (hasLocationPermission()) {
            try {
                val location = getLocationFromFusedClient()
                Result.success(Location(location.latitude, location.longitude))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(SecurityException("Permiso de ubicación no concedido"))
        }
    }

    private fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    override suspend fun getAddressFromLocation(lat: Double, lng: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                var result = "Sin dirección"
                geocoder.getFromLocation(lat, lng, 1) { addresses ->
                    result = formatAddress(addresses.firstOrNull())
                }
                result
            } else {
                // Android 12 o menor
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                formatAddress(addresses?.firstOrNull())
            }
        } catch (e: Exception) {
            "Sin dirección"
        }
    }

    private fun formatAddress(address: Address?): String {
        if (address == null) return "Sin dirección"
        return buildString {
            address.thoroughfare?.let { append(it) }
            address.subThoroughfare?.let { append(" $it") }
            address.subLocality?.let { append(", $it") }
            address.locality?.let { append(", $it") }
            address.adminArea?.let { append(", $it") }
        }.ifBlank { "Sin dirección" }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private suspend fun getLocationFromFusedClient(): android.location.Location =
        suspendCancellableCoroutine { continuation ->
            val cancellationTokenSource = CancellationTokenSource()
            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(location)
                } else {
                    continuation.resumeWithException(Exception("Ubicación no disponible"))
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
}