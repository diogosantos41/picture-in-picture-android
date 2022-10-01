package com.didexcodes.pictureinpicture.di

import com.didexcodes.pictureinpicture.data.DefaultPipReceiver
import com.didexcodes.pictureinpicture.domain.PipReceiver
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
    fun providePipReceiver(): PipReceiver {
        return DefaultPipReceiver()
    }
}