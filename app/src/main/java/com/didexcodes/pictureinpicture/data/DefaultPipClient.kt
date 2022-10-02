package com.didexcodes.pictureinpicture.data

import com.didexcodes.pictureinpicture.domain.PipClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultPipClient : PipClient {

    override fun getPipUpdate(): Flow<Boolean> {
        return callbackFlow {
            val pipReceiverInterface = object : PipBroadcastReceiver.PipReceiverInterface {
                override fun onPipReceive() {
                    launch { send(true) }
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