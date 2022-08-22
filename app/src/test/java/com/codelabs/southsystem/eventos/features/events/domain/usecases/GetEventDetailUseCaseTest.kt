package com.codelabs.southsystem.eventos.features.events.domain.usecases

import com.codelabs.southsystem.eventos.core.errors.ServerException
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository
import com.codelabs.southsystem.eventos.utils.fakeEventEntity
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
class GetEventDetailUseCaseTest {
    private val repository = mock<EventRepository>()
    private val serverException = ServerException()
    private val params = GetEventDetailUseCase.Params("1")

    private lateinit var getEventDetailUseCase: GetEventDetailUseCase

    @Before
    fun setUp() {
        getEventDetailUseCase = GetEventDetailUseCase(repository)
    }

    @Test
    fun `it should request event detail when calling repository one time`() = runTest {
        whenever(repository.getEvent(params.id)).thenReturn(Result.success(fakeEventEntity))

        getEventDetailUseCase(params)

        verify(repository, times(1)).getEvent(params.id)
    }

    @Test
    fun `it should return an event entity on successful repository call`() = runTest {
        whenever(repository.getEvent(params.id)).thenReturn(Result.success(fakeEventEntity))

        val result = getEventDetailUseCase(params)

        assertEquals(Result.success(fakeEventEntity), result)
    }

    @Test
    fun `it should return the correct exception on unsuccessful repository call`() = runTest {
        whenever(repository.getEvent(params.id)).thenReturn(Result.failure(serverException))

        val result = getEventDetailUseCase(params)

        assertEquals(Result.failure<Any>(serverException), result)
    }
}