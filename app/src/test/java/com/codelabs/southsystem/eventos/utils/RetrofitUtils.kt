package com.codelabs.southsystem.eventos.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

val fakeRetrofitResponseError: Response<Any> = Response.error(
    500,
    ResponseBody.create("application/json".toMediaTypeOrNull(),"")
)