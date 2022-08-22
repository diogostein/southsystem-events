package com.codelabs.southsystem.eventos.features.events.domain.usecases

import com.codelabs.southsystem.eventos.core.errors.ServerException
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository
import com.codelabs.southsystem.eventos.utils.fakeCheckInRequest
import com.codelabs.southsystem.eventos.utils.fakeCodeResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PerformCheckInUseCaseTest {
    private val repository = mock<EventRepository>()
    private val serverException = ServerException()
    private val invalidEmailException = PerformCheckInUseCase.InvalidEmailException()
    private val checkInRequest = fakeCheckInRequest
    private val params = PerformCheckInUseCase.Params(
        checkInRequest.eventId, checkInRequest.name, checkInRequest.email)

    private lateinit var performCheckInUseCase: PerformCheckInUseCase

    @Before
    fun setUp() {
        performCheckInUseCase = PerformCheckInUseCase(repository)
    }

    @Test
    fun `it should request event detail when calling repository one time`() = runTest {
        whenever(repository.performCheckIn(checkInRequest))
            .thenReturn(Result.success(fakeCodeResponse))

        performCheckInUseCase(params)

        verify(repository, times(1)).performCheckIn(checkInRequest)
    }

    @Test
    fun `it should return an event entity on successful repository call`() = runTest {
        whenever(repository.performCheckIn(checkInRequest))
            .thenReturn(Result.success(fakeCodeResponse))

        val result = performCheckInUseCase(params)

        assertEquals(Result.success(fakeCodeResponse), result)
    }

    @Test
    fun `it should return the correct exception on unsuccessful repository call`() = runTest {
        whenever(repository.performCheckIn(checkInRequest))
            .thenReturn(Result.failure(serverException))

        val result = performCheckInUseCase(params)

        assertEquals(Result.failure<Any>(serverException), result)
    }

    @Test
    fun `it should return InvalidEmailException when passing params with invalid email`() = runTest {
        whenever(repository.performCheckIn(checkInRequest))
            .thenReturn(Result.success(fakeCodeResponse))

        val result = performCheckInUseCase(params.copy(email = "john.doe"))

        assertEquals(invalidEmailException.message, result.exceptionOrNull()?.message)
    }
}