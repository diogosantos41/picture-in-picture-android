package com.didexcodes.pictureinpicture.presentation

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.didexcodes.pictureinpicture.R
import com.didexcodes.pictureinpicture.utils.findActivity
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PipScreen(viewModel: PipViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val state = viewModel.state.value
    val videoView = VideoView(context, null)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PipViewModel.UiEvent.PlayVideo -> {
                    videoView.start()
                }
                is PipViewModel.UiEvent.StopVideo -> {
                    videoView.pause()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            factory = {
                videoView.apply {
                    setVideoURI(Uri.parse("android.resource://${context.packageName}/${R.raw.video}"))
                    start()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    (context.findActivity() as MainActivity).videoViewBounds = it
                        .boundsInWindow()
                        .toAndroidRect()
                }
        )
        Spacer(modifier = Modifier.height(15.dp))
        IconButton(
            onClick = {
                viewModel.toggleVideoPlay()
            },
        ) {
            Icon(
                painter = painterResource(id = state.mediaIconId),
                contentDescription = "media icon",
                modifier = Modifier.size(80.dp)
            )
        }
    }
}