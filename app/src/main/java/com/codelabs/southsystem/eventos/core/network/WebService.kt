package com.codelabs.southsystem.eventos.core.network

import com.codelabs.southsystem.eventos.core.network.request.CheckInRequest
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.core.network.response.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WebService {

    @GET("api/events")
    suspend fun getEvents(): List<EventResponse>

    @GET("api/events/{id}")
    suspend fun getEvent(@Path("id") id: String): EventResponse

    @POST("api/checkin")
    suspend fun checkIn(@Body request: CheckInRequest): CodeResponse

}