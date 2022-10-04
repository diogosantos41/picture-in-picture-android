package com.didexcodes.pictureinpicture.presentation

import android.graphics.Rect
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
import com.didexcodes.pictureinpicture.utils.mainActivity
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PipScreen(viewModel: PipViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val videoView = VideoView(context, null)
    val videoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.video}")

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PipViewModel.UiEvent.PlayVideo -> {
                    videoView.start()
                    context.mainActivity().updatePipParams(videoPlaying = true)
                }
                is PipViewModel.UiEvent.PauseVideo -> {
                    videoView.pause()
                    context.mainActivity().updatePipParams(videoPlaying = false)
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
        VideoViewUI(
            videoView = videoView,
            videoUri = videoUri,
            onViewBoundsRect = { context.mainActivity().videoViewBounds = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        MediaButtons(
            modifier = Modifier.fillMaxWidth(),
            onPlayClick = { viewModel.playVideo() },
            onPauseClick = { viewModel.pauseVideo() })
    }
}

@Composable
fun VideoViewUI(
    videoView: VideoView, videoUri: Uri, onViewBoundsRect: (Rect) -> Unit
) {
    AndroidView(
        factory = {
            videoView.apply {
                setVideoURI(videoUri)
                setOnPreparedListener { it.isLooping = true }
                start()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                onViewBoundsRect(
                    it
                        .boundsInWindow()
                        .toAndroidRect()
                )
            }
    )
}

@Composable
fun MediaButtons(
    modifier: Modifier,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onPlayClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "media icon",
                modifier = Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        IconButton(
            onClick = onPauseClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "media icon",
                modifier = Modifier.size(80.dp)
            )
        }
    }
}
