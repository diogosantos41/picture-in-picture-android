package com.didexcodes.pictureinpicture

import android.graphics.Rect
import android.graphics.drawable.Icon
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PipViewModel @Inject constructor() : ViewModel() {


    data class ViewState(
        val mediaIcon: Icon? = null
    )

    private var videoViewBounds = Rect()

    private val _state = mutableStateOf(
        ViewState()
    )
    private val state: State<ViewState> = _state


    fun togglePlayVideo() {

    }

}