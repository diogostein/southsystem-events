package com.codelabs.southsystem.eventos.core.network

import com.codelabs.southsystem.eventos.core.network.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("api/events")
    suspend fun getEvents(): List<EventResponse>

    @GET("api/events/{id}")
    suspend fun getEvent(@Path("id") id: String): EventResponse

}