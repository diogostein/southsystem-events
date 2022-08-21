package com.codelabs.southsystem.eventos.features.events.domain.entities

import java.math.BigDecimal
import java.util.*

data class Event(
    val id: String,
    val title: String,
    val date: Date,
    val description: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val price: BigDecimal,
)
