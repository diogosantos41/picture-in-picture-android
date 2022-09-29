package com.didexcodes.pictureinpicture

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PipScreen(viewModel: PipViewModel = hiltViewModel()) {

    val context = LocalContext.current

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