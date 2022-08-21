package com.codelabs.southsystem.eventos.features.events.data

import com.codelabs.southsystem.eventos.core.errors.ServerException
import com.codelabs.southsystem.eventos.core.network.WebService
import com.codelabs.southsystem.eventos.core.network.request.CheckInRequest
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.core.network.response.toEntity
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository
import java.lang.Exception

class EventRepositoryImpl(
    private val webService: WebService
) : EventRepository {

    override suspend fun getEvents(): Result<List<Event>> {
        return try {
            webService.getEvents().let { response ->
                Result.success(response.map { it.toEntity() })
            }
        } catch (e: Exception) {
            Result.failure(ServerException(e.message))
        }
    }

    override suspend fun getEvent(id: String): Result<Event> {
        return try {
            webService.getEvent(id).let { response ->
                Result.success(response.toEntity())
            }
        } catch (e: Exception) {
            Result.failure(ServerException(e.message))
        }
    }

    override suspend fun performCheckIn(request: CheckInRequest): Result<CodeResponse> {
        return try {
            webService.checkIn(request).let { response ->
                Result.success(response)
            }
        } catch (e: Exception) {
            Result.failure(ServerException(e.message))
        }
    }

}