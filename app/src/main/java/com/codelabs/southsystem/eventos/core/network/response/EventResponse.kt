package com.codelabs.southsystem.eventos.core.network.response

import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class EventResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("price")
    val price: BigDecimal,
)

fun EventResponse.toEntity(): Event {
    return Event(
        id = id,
        title = title,
        date = Date(date),
        description = description,
        image = image,
        latitude = latitude,
        longitude = longitude,
        price = price
    )
}