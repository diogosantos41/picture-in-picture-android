package com.didexcodes.pictureinpicture.presentation

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import com.didexcodes.pictureinpicture.R
import com.didexcodes.pictureinpicture.data.PipBroadcastReceiver
import com.didexcodes.pictureinpicture.ui.theme.PictureInPictureTheme
import com.didexcodes.pictureinpicture.utils.Constant
import com.didexcodes.pictureinpicture.utils.Constant.CONTROL_PAUSE
import com.didexcodes.pictureinpicture.utils.Constant.CONTROL_PLAY
import com.didexcodes.pictureinpicture.utils.isPipSupported
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var videoViewBounds: Rect? = null
    private var isVideoPLaying: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PictureInPictureTheme {
                PipScreen()
            }
        }
    }

    fun updatePipParams(videoPlaying: Boolean): PictureInPictureParams? {
        isVideoPLaying = videoPlaying
        val aspectRatio = Rational(16, 9)
        val pictureInPictureParams = PictureInPictureParams.Builder()
            .setActions(
                listOf(
                    if (isVideoPLaying) {
                        createRemoteAction(
                            R.drawable.ic_pause,
                            "Pause",
                            CONTROL_PAUSE
                        )
                    } else {
                        createRemoteAction(
                            R.drawable.ic_play,
                            "Play",
                            CONTROL_PLAY
                        )
                    }

                )
            )
            .setSourceRectHint(videoViewBounds)
            .setAspectRatio(aspectRatio)
            .build()
        setPictureInPictureParams(pictureInPictureParams)
        return pictureInPictureParams
    }

    private fun createRemoteAction(
        @DrawableRes icon: Int,
        title: String,
        controlType: Int
    ): RemoteAction {
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            controlType,
            Intent(
                applicationContext,
                PipBroadcastReceiver::class.java
            ).putExtra(Constant.ACTION_CONTROL, controlType),
            PendingIntent.FLAG_IMMUTABLE
        )
        val remoteAction = RemoteAction(
            Icon.createWithResource(this, icon),
            title,
            title,
            pendingIntent
        )
        val list = ArrayList<RemoteAction>()
        list.add(remoteAction)
        return remoteAction
    }

    private fun minimizeVideo() {
        updatePipParams(isVideoPLaying)?.let { params ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(params)
            }
        }
    }

    override fun onBackPressed() {
        if (!applicationContext.isPipSupported()) {
            super.onBackPressed()
        }
        minimizeVideo()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!applicationContext.isPipSupported()) {
            return
        }
        minimizeVideo()
    }


}
