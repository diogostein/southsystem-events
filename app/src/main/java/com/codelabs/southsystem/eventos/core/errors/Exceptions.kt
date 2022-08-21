package com.codelabs.southsystem.eventos.core.errors

open class ServerException(message: String?) : Exception(message)

open class BusinessException(message: String?) : Exception(message)