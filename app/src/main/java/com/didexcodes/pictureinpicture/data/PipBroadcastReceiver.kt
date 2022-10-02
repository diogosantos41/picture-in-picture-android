package com.didexcodes.pictureinpicture.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class PipBroadcastReceiver : BroadcastReceiver() {

    private var pipReceiverInterface: PipReceiverInterface? = null

    interface PipReceiverInterface {
        fun onPipReceive()
    }

    fun registerListener(pipReceiverInterface: PipReceiverInterface) {
        this.pipReceiverInterface = pipReceiverInterface
    }

    fun closeReceiver() {
        pipReceiverInterface = null
        abortBroadcast()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        pipReceiverInterface?.onPipReceive()
    }
}
