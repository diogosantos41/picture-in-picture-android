package com.didexcodes.pictureinpicture.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.didexcodes.pictureinpicture.utils.Constant


class PipBroadcastReceiver : BroadcastReceiver() {

    private var pipReceiverInterface: PipReceiverInterface? = null

    interface PipReceiverInterface {
        fun onPipReceive(action: Int)
    }

    fun registerListener(pipReceiverInterface: PipReceiverInterface) {
        this.pipReceiverInterface = pipReceiverInterface
    }

    fun closeReceiver() {
        pipReceiverInterface = null
        abortBroadcast()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) {
            return
        }
        pipReceiverInterface?.onPipReceive(intent.getIntExtra(Constant.ACTION_CONTROL, 0))
    }
}
