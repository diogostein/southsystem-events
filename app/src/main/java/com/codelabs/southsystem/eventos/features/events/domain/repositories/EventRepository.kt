package com.codelabs.southsystem.eventos.features.events.domain.repositories

import com.codelabs.southsystem.eventos.core.network.request.CheckInRequest
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event

interface EventRepository {
    suspend fun getEvents(): Result<List<Event>>
    suspend fun getEvent(id: String): Result<Event>
    suspend fun performCheckIn(request: CheckInRequest): Result<CodeResponse>
}