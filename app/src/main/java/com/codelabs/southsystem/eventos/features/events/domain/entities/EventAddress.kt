package com.codelabs.southsystem.eventos.features.events.domain.entities

data class EventAddress(
    private val street: String?,
    private val number: String?,
    private val district: String?,
    private val city: String?,
    private val state: String?,
    private val postalCode: String?
) {
    val formattedAddress: String get() = "$street, $number - $district - $city, $state"
}
