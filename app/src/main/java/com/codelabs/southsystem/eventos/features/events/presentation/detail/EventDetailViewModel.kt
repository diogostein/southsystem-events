package com.codelabs.southsystem.eventos.features.events.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.southsystem.eventos.core.UiState
import com.codelabs.southsystem.eventos.core.network.response.CodeResponse
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.usecases.GetEventDetailUseCase
import com.codelabs.southsystem.eventos.features.events.domain.usecases.PerformCheckInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val performCheckInUseCase: PerformCheckInUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<UiState<Event>>()
    val state: LiveData<UiState<Event>> = _state

    private val _checkInState = MutableLiveData<UiState<CodeResponse>>()
    val checkInState: LiveData<UiState<CodeResponse>> = _checkInState

    fun getEventDetail(id: String) {
        _state.value = UiState.Loading

        viewModelScope.launch {
            _state.value = getEventDetailUseCase(GetEventDetailUseCase.Params(id)).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message) }
            )
        }
    }

    fun performCheckIn(eventId: String, name: String, email: String) {
        _checkInState.value = UiState.Loading

        viewModelScope.launch {
            _checkInState.value = performCheckInUseCase(
                PerformCheckInUseCase.Params(eventId, name, email)
            ).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message) }
            )
        }
    }

}