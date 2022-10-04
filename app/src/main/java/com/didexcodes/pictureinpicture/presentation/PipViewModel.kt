package com.didexcodes.pictureinpicture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didexcodes.pictureinpicture.domain.PipClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PipViewModel @Inject constructor(private val pipClient: PipClient) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observePipAction()
    }

    private fun observePipAction() {
        pipClient
            .isToPlayVideo()
            .onEach {
                if (it) playVideo() else pauseVideo()
            }.launchIn(viewModelScope)
    }


    fun playVideo() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.PlayVideo)
        }
    }

     fun pauseVideo() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.PauseVideo)
        }
    }

    sealed interface UiEvent {
        object PlayVideo : UiEvent
        object PauseVideo : UiEvent
    }
}