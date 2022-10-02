package com.didexcodes.pictureinpicture.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didexcodes.pictureinpicture.R
import com.didexcodes.pictureinpicture.domain.PipClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PipViewModel @Inject constructor(private val pipClient: PipClient) : ViewModel() {

    private val _state = mutableStateOf(PipState(mediaIconId = R.drawable.ic_pause))
    val state: State<PipState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var isVideoPlaying = true

    init {
        observePipClick()
    }

    private fun observePipClick() {
        pipClient
            .getPipUpdate()
            .catch { e -> e.printStackTrace() }
            .onEach {
                toggleVideoPlay()
            }.launchIn(viewModelScope)
    }

    fun toggleVideoPlay() {
        viewModelScope.launch {
            if (isVideoPlaying) {
                _eventFlow.emit(UiEvent.StopVideo)
                _state.value = state.value.copy(
                    mediaIconId = R.drawable.ic_play,
                )
            } else {
                _eventFlow.emit(UiEvent.PlayVideo)
                _state.value = state.value.copy(
                    mediaIconId = R.drawable.ic_pause,
                )
            }
            isVideoPlaying = !isVideoPlaying
        }
    }

    sealed interface UiEvent {
        object PlayVideo : UiEvent
        object StopVideo : UiEvent
    }


}