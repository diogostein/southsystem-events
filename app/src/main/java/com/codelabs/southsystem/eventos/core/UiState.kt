package com.codelabs.southsystem.eventos.core

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    class Success<T>(val value: T? = null) : UiState<T>()
    class Error(val message: String?) : UiState<Nothing>()
}