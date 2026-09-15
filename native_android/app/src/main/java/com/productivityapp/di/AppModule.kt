package com.productivityapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): Any {
        // TODO: Replace Any with actual AppDatabase class when available
        return Any()
    }

    @Provides
    @Singleton
    fun provideOmniEngine(): Any {
        // TODO: Replace Any with actual OmniEngine class when available
        return Any()
    }
}
