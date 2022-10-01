package com.didexcodes.pictureinpicture.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didexcodes.pictureinpicture.domain.PipReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PipViewModel @Inject constructor(private val pipReceiver: PipReceiver) : ViewModel() {

    private val _state = mutableStateOf(PipState())
    private val state: State<PipState> = _state

    private var isVideoPlaying = true


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observePipClick()
    }

    private fun observePipClick() {
        pipReceiver.toggleVideoPlay().onEach {
            toggleVideoPlay()
        }.launchIn(viewModelScope)
    }

    private fun toggleVideoPlay() {
        viewModelScope.launch {
            if (isVideoPlaying) {
                _eventFlow.emit(UiEvent.StopVideo)
                _state.value = state.value.copy(
                    mediaIcon = null,
                )
            } else {
                _eventFlow.emit(UiEvent.PlayVideo)
                _state.value = state.value.copy(
                    mediaIcon = null,
                )
            }
        }
        isVideoPlaying = !isVideoPlaying
    }

    sealed interface UiEvent {
        object PlayVideo : UiEvent
        object StopVideo : UiEvent
    }


}