package com.didexcodes.pictureinpicture.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.didexcodes.pictureinpicture.domain.PipReceiver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DefaultPipReceiver : PipReceiver, BroadcastReceiver() {

    private var pipReceiverInterface: PipReceiverInterface? = null

    override fun toggleVideoPlay(): Flow<Boolean> {
        return callbackFlow {
            pipReceiverInterface = object : PipReceiverInterface {
                override fun onPipReceive() {
                    trySend(true)
                }
            }
            awaitClose {}
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        pipReceiverInterface?.onPipReceive()
    }

    interface PipReceiverInterface {
        fun onPipReceive()
    }
}