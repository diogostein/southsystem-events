package com.codelabs.southsystem.eventos.features.events.domain.usecases

import com.codelabs.southsystem.eventos.core.UseCase
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository

class GetEventDetailUseCase(
    private val repository: EventRepository
) : UseCase<GetEventDetailUseCase.Params, Result<Event>> {

    override suspend fun invoke(params: Params): Result<Event> {
        return repository.getEvent(params.id)
    }

    data class Params(val id: String)
}