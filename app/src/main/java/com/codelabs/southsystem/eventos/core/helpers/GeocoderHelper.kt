package com.codelabs.southsystem.eventos.core.helpers

import android.content.Context
import android.location.Geocoder
import com.codelabs.southsystem.eventos.features.events.domain.entities.EventAddress
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GeocoderHelper(
    context: Context,
    private val dispatcher: CoroutineDispatcher,
) {
    private val geocoder = Geocoder(context)

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getEventAddressFromCoordinates(lat: Double, lng: Double) = withContext(dispatcher) {
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val address = addresses.firstOrNull()

            if (address != null) {
                with(address) {
                    EventAddress(
                        street = thoroughfare,
                        number = subThoroughfare,
                        district = subLocality,
                        city = subAdminArea,
                        state = adminArea,
                        postalCode = postalCode,
                    )
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}