package com.policy.shower.di

import android.app.Application
import com.policy.shower.core.util.crypto_manager.CryptoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PolicyModule {

    @Provides
    @Singleton
    fun provideLinkDatabase(app : Application) : com.policy.shower.core.data.local.LinkDatabase {
        return com.policy.shower.core.data.local.LinkSharedPref(app)
    }

    @Provides
    @Singleton
    fun provideCryptoManager() : CryptoManager {
        return com.policy.shower.core.util.crypto_manager.CryptoManagerImpl()
    }
    
}