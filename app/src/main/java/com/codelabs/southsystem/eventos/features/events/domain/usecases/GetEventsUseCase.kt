package com.codelabs.southsystem.eventos.features.events.domain.usecases

import com.codelabs.southsystem.eventos.core.UseCase
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository

class GetEventsUseCase(
    private val repository: EventRepository
) : UseCase<UseCase.NoParams, Result<List<Event>>> {

    override suspend fun invoke(params: UseCase.NoParams): Result<List<Event>> {
        return repository.getEvents()
    }

}