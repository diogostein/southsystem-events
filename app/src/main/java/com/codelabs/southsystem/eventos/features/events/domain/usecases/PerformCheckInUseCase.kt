package com.codelabs.southsystem.eventos.features.events.domain.usecases

import androidx.core.util.PatternsCompat
import com.codelabs.southsystem.eventos.core.UseCase
import com.codelabs.southsystem.eventos.core.errors.BusinessException
import com.codelabs.southsystem.eventos.core.network.request.CheckInRequest
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository

class PerformCheckInUseCase(
    private val repository: EventRepository,
) : UseCase<PerformCheckInUseCase.Params, Result<CodeResponse>> {

    override suspend fun invoke(params: Params): Result<CodeResponse> {
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(params.email).matches()) {
            return Result.failure(InvalidEmailException())
        }

        return repository.performCheckIn(params.toRequest())
    }

    class InvalidEmailException(message: String? = "E-mail inválido") : BusinessException(message)

    data class Params(
        val eventId: String,
        val name: String,
        val email: String,
    ) {
        fun toRequest() = CheckInRequest(eventId, name, email)
    }
}