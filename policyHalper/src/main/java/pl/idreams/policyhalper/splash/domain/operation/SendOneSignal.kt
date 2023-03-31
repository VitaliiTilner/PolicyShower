package com.policy.shower.splash.domain.operation

import com.policy.shower.core.util.crypto_manager.CryptoManager
import com.onesignal.OneSignal

class SendOneSignal {
    operator fun invoke(push: String?, appsId: String?,crypto : CryptoManager) {
        OneSignal.setExternalUserId(appsId ?: "")
        OneSignal.sendTag(
            "${crypto.decrypt("acj")}_${crypto.decrypt("ixx")}",
            (push ?: crypto.decrypt("wzoivqk"))
        )
    }
}