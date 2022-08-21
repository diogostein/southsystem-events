package com.codelabs.southsystem.eventos.core

interface UseCase<P, R> {
    suspend operator fun invoke(params: P): R

    object NoParams
}