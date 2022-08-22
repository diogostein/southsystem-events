package com.codelabs.southsystem.eventos.features.events.data

import com.codelabs.southsystem.eventos.core.errors.ServerException
import com.codelabs.southsystem.eventos.core.network.WebService
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository
import com.codelabs.southsystem.eventos.utils.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class EventRepositoryTest {
    private val webService = mock<WebService>()
    private val httpException = HttpException(fakeRetrofitResponseError)
    private val serverException = ServerException()

    private lateinit var repository: EventRepository

    @Before
    fun setUp() {
        repository = EventRepositoryImpl(webService)
    }

    @Test
    fun `it should fetch events from network when calling web service one time`() = runTest {
        whenever(webService.getEvents()).thenReturn(listOf(fakeEventResponse))

        repository.getEvents()

        verify(webService, times(1)).getEvents()
    }

    @Test
    fun `it should return a list of event entities on successful network call`() = runTest {
        whenever(webService.getEvents()).thenReturn(listOf(fakeEventResponse))

        val result = repository.getEvents()

        assertEquals(Result.success(listOf(fakeEventEntity)), result)
    }

    @Test
    fun `it should return an event entity on successful network call`() = runTest {
        whenever(webService.getEvent("1")).thenReturn(fakeEventResponse)

        val result = repository.getEvent("1")

        assertEquals(Result.success(fakeEventEntity), result)
    }

    @Test
    fun `it should return an code response on successful check-in`() = runTest {
        whenever(webService.checkIn(fakeCheckInRequest)).thenReturn(fakeCodeResponse)

        val result = repository.performCheckIn(fakeCheckInRequest)

        assertEquals(Result.success(fakeCodeResponse), result)
    }

    @Test
    fun `it should return the correct exception on unsuccessful fetch of events`() = runTest {
        whenever(webService.getEvents()).thenThrow(httpException)

        val getEvents = repository.getEvents()

        assertEquals(serverException.message, getEvents.exceptionOrNull()?.message)
    }

    @Test
    fun `it should return the correct exception on unsuccessful fetch of event detail`() = runTest {
        whenever(webService.getEvent("1")).thenThrow(httpException)

        val getEvent = repository.getEvent("1")

        assertEquals(serverException.message, getEvent.exceptionOrNull()?.message)
    }

    @Test
    fun `it should return the correct exception on unsuccessful check-in`() = runTest {
        whenever(webService.checkIn(fakeCheckInRequest)).thenThrow(httpException)

        val performCheckIn = repository.performCheckIn(fakeCheckInRequest)

        assertEquals(serverException.message, performCheckIn.exceptionOrNull()?.message)
    }
}