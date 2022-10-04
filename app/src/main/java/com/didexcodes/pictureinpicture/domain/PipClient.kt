package com.didexcodes.pictureinpicture.domain

import kotlinx.coroutines.flow.Flow

interface PipClient {

    fun isToPlayVideo(): Flow<Boolean>

}