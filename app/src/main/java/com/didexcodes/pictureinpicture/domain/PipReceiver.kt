package com.didexcodes.pictureinpicture.domain

import kotlinx.coroutines.flow.Flow

interface PipReceiver {

    fun toggleVideoPlay(): Flow<Boolean>

}