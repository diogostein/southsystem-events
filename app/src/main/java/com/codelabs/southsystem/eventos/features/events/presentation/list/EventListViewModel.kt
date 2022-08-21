package com.codelabs.southsystem.eventos.features.events.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.southsystem.eventos.core.UiState
import com.codelabs.southsystem.eventos.core.UseCase
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.usecases.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<UiState<List<Event>>>()
    val state: LiveData<UiState<List<Event>>> = _state

    init {
        getEvents()
    }

    fun getEvents() {
        _state.value = UiState.Loading

        viewModelScope.launch {
            _state.value = getEventsUseCase(UseCase.NoParams).fold(
               onSuccess = { UiState.Success(it) },
               onFailure = { UiState.Error(it.message) }
           )
        }
    }

}