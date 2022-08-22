package com.codelabs.southsystem.eventos.utils

import com.codelabs.southsystem.eventos.core.network.request.CheckInRequest
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.core.network.response.EventResponse
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import java.util.*

val fakeEventResponse = EventResponse(
    id = "1",
    title = "title",
    date = 1534784400000,
    description = "description",
    image = "image",
    latitude = 0.0,
    longitude = 0.0,
    price = 0.0.toBigDecimal()
)

val fakeCodeResponse = CodeResponse(
    code = "200"
)

val fakeEventEntity = Event(
    id = "1",
    title = "title",
    date = Date(1534784400000),
    description = "description",
    image = "image",
    latitude = 0.0,
    longitude = 0.0,
    price = 0.0.toBigDecimal()
)

val fakeCheckInRequest = CheckInRequest(
    eventId = "1",
    name = "John Doe",
    email = "john@doe.com",
)