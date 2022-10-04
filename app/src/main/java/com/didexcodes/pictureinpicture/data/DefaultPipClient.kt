package com.didexcodes.pictureinpicture.data

import com.didexcodes.pictureinpicture.domain.PipClient
import com.didexcodes.pictureinpicture.utils.Constant.CONTROL_PAUSE
import com.didexcodes.pictureinpicture.utils.Constant.CONTROL_PLAY
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultPipClient : PipClient {

    override fun isToPlayVideo(): Flow<Boolean> {
        return callbackFlow {
            val pipReceiverInterface = object : PipBroadcastReceiver.PipReceiverInterface {
                override fun onPipReceive(action: Int) {
                    launch {
                        when (action) {
                            CONTROL_PLAY -> {
                                send(true) // isToPlayVideo = true
                            }
                            CONTROL_PAUSE -> {
                                send(false) // isToPlayVideo = false
                            }
                        }
                    }
                }
            }
            val broadcastReceiver = PipBroadcastReceiver()
            broadcastReceiver.registerListener(pipReceiverInterface)
            awaitClose {
                broadcastReceiver.closeReceiver()
            }
        }
    }
}