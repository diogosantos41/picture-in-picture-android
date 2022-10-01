package com.didexcodes.pictureinpicture.presentation

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.didexcodes.pictureinpicture.R
import com.didexcodes.pictureinpicture.findActivity
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PipScreen(viewModel: PipViewModel = hiltViewModel()) {

    // TODO di issue maybe? "java.lang.RuntimeException: Cannot create an instance of class com.didexcodes.pictureinpicture.presentation.PipViewModel"

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PipViewModel.UiEvent.PlayVideo -> {

                }
                is PipViewModel.UiEvent.StopVideo -> {

                }
            }
        }
    }

    AndroidView(
        factory = {
            VideoView(context, null).apply {
                setVideoURI(Uri.parse("android.resource://${context.packageName}/${R.raw.sample}"))
                start()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .onGloballyPositioned {
                (context.findActivity() as MainActivity).videoViewBounds = it
                    .boundsInWindow()
                    .toAndroidRect()
            }
    )
}