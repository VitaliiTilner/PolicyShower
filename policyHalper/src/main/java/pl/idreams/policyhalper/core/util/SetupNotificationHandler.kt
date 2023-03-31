package com.policy.shower.core.util

import android.content.Intent
import androidx.activity.ComponentActivity
import com.onesignal.OneSignal
import com.policy.shower.web.ui.WebActivityImpl

fun setupNotificationHandler(
    activity: ComponentActivity,
    title: String?,
) {
    val crypto = com.policy.shower.core.util.crypto_manager.CryptoManagerImpl()

    OneSignal.setNotificationOpenedHandler {
        val launchUrl: String? = it?.notification?.launchURL

        if (launchUrl == null || launchUrl == "null" || launchUrl == "") {
            return@setNotificationOpenedHandler
        }

        val isValidUrl =
            launchUrl.startsWith("${crypto.decrypt("eee")}.") ||
                    launchUrl.startsWith("${crypto.decrypt("pbbx")}://") ||
                    launchUrl.startsWith("${crypto.decrypt("pbbxa")}://")

        if (isValidUrl) {
            OneSignal.setNotificationOpenedHandler(null)

            val intent = Intent(activity, WebActivityImpl::class.java)

            intent.putExtra(com.policy.shower.core.common.Const.LINK_KEY, launchUrl)
            intent.putExtra(com.policy.shower.core.common.Const.TRACKER_KEY, true)
            intent.putExtra(com.policy.shower.core.common.Const.TITLE_KEY, title)

            activity.apply {
                startActivity(intent)
                finish()
            }
        }
    }
}