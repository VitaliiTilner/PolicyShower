package com.policy.shower.web.di

import com.policy.shower.web.opearator.*
import com.policy.shower.web.opearator.CreateFile
import com.policy.shower.web.opearator.HandleOnBackPressed
import com.policy.shower.web.opearator.SaveLink
import com.policy.shower.web.opearator.SetWebSettings
import com.policy.shower.web.opearator.WebOperator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object WebModule {

    @Provides
    @ActivityScoped
    fun provideWebOperator(
        linkDatabase: com.policy.shower.core.data.local.LinkDatabase,
    ): WebOperator {
        return WebOperator(
            createFile = CreateFile(),
            handleOnBackPressed = HandleOnBackPressed(),
            setWebSettings = SetWebSettings(),
            saveLink = SaveLink(linkDatabase),
        )
    }
}