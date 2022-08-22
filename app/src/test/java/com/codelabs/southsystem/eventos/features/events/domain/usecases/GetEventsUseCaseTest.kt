package com.codelabs.southsystem.eventos.features.events.domain.usecases

import com.codelabs.southsystem.eventos.core.UseCase
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
class GetEventsUseCaseTest {
    private val repository = mock<EventRepository>()
    private val serverException = ServerException()

    private lateinit var getEventsUseCase: GetEventsUseCase

    @Before
    fun setUp() {
        getEventsUseCase = GetEventsUseCase(repository)
    }

    @Test
    fun `it should request events when calling repository one time`() = runTest {
        whenever(repository.getEvents()).thenReturn(Result.success(listOf(fakeEventEntity)))

        getEventsUseCase(UseCase.NoParams)

        verify(repository, times(1)).getEvents()
    }

    @Test
    fun `it should return a list of event entities on successful repository call`() = runTest {
        whenever(repository.getEvents()).thenReturn(Result.success(listOf(fakeEventEntity)))

        val result = getEventsUseCase(UseCase.NoParams)

        assertEquals(Result.success(listOf(fakeEventEntity)), result)
    }

    @Test
    fun `it should return the correct exception on unsuccessful repository call`() = runTest {
        whenever(repository.getEvents()).thenReturn(Result.failure(serverException))

        val result = getEventsUseCase(UseCase.NoParams)

        assertEquals(Result.failure<Any>(serverException), result)
    }
}