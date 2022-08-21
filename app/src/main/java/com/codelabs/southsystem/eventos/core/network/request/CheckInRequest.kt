package com.codelabs.southsystem.eventos.core.network.request

data class CheckInRequest(
    val eventId: String,
    val name: String,
    val email: String,
)
