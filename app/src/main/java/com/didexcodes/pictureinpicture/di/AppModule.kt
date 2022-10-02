package com.didexcodes.pictureinpicture.di

import com.didexcodes.pictureinpicture.data.DefaultPipClient
import com.didexcodes.pictureinpicture.domain.PipClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun providePipClient(): PipClient {
        return DefaultPipClient()
    }
}