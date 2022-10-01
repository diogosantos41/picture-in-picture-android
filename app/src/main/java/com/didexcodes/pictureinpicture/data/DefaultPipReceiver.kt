package com.didexcodes.pictureinpicture.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.didexcodes.pictureinpicture.domain.PipReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultPipReceiver : PipReceiver, BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        toggleVideoPlay()
    }

    override fun toggleVideoPlay() : Flow<Boolean> {
        return flow { emit(true) }
    }
}