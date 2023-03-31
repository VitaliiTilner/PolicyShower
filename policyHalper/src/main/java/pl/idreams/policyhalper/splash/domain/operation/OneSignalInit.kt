package com.policy.shower.splash.domain.operation

import android.content.Context
import com.onesignal.OneSignal

class OneSignalInit(private val context : Context){
    operator fun invoke (oneSignalId : String){
        OneSignal.initWithContext(context)
        OneSignal.setAppId(oneSignalId)
        OneSignal.setLogLevel(
            OneSignal.LOG_LEVEL.VERBOSE,
            OneSignal.LOG_LEVEL.NONE
        )
    }
}