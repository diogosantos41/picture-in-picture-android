package com.didexcodes.pictureinpicture.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.didexcodes.pictureinpicture.domain.PipReceiver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DefaultPipReceiver : PipReceiver, BroadcastReceiver() {

    private lateinit var pipReceiverInterface: PipReceiverInterface

    override fun toggleVideoPlay(): Flow<Boolean> {
        return _pipReceiver
    }

    private val _pipReceiver = callbackFlow {
        pipReceiverInterface = object : PipReceiverInterface {
            override fun onReceive() {
                trySend(true)
            }
        }
        awaitClose {}
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        pipReceiverInterface.onReceive()
    }

    interface PipReceiverInterface {
        fun onReceive()
    }
}