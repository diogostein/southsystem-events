package com.codelabs.southsystem.eventos.core.errors

open class ServerException(message: String? = "Falha de conexão com servidor. Por favor, tente novamente.") : Exception(message)

open class BusinessException(message: String?) : Exception(message)