package com.policy.shower.splash

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import com.policy.shower.core.util.Collector
import com.policy.shower.core.util.crypto_manager.CryptoManager
import com.policy.shower.core.util.setupNotificationHandler
import com.policy.shower.core.util.web.WebActivity
import com.policy.shower.splash.domain.model.AppsData
import pl.idreams.policyshower.splash.domain.model.PolicyValues
import com.policy.shower.splash.domain.model.ReferrerData
import com.policy.shower.splash.domain.model.ValuesData
import com.policy.shower.splash.domain.operation.SplashOperator
import com.policy.shower.web.ui.WebActivityImpl
import kotlinx.coroutines.*

class PolicyShower(
    private val policyValues: PolicyValues
) {
    private val crypto: CryptoManager =
        com.policy.shower.core.util.crypto_manager.CryptoManagerImpl()

    suspend fun checkIfNeedToShowPolicy(
        activity: ComponentActivity,
        gameActivityClass: Class<out Activity>,
        tracker: String,
        title: String,
        titleData : String,
    ) {
        val webActivity: Class<out WebActivity> = WebActivityImpl::class.java
        val splashOperator = SplashOperator(webActivity = webActivity, activity)

        setupNotificationHandler(activity, title)

        delay(1300)

        com.policy.shower.core.GameClassData.gameClass = gameActivityClass

        withContext(Dispatchers.IO){
            splashOperator.oneSignalInit(policyValues.OSI)
        }

        val savedLink = withContext(Dispatchers.IO) {
            splashOperator.getLink()
        }

        if (splashOperator.validLink(savedLink) && savedLink != null) {
            splashOperator.openWeb(activity, savedLink, true, title)
        } else {
            if (tracker == "" || tracker == "null") {
                val gameIntent = Intent(activity, gameActivityClass)
                activity.startActivity(gameIntent)
            } else {
                @Suppress("DeferredResultUnused")
                withContext(Dispatchers.IO) {
                    val collector = Collector(
                        setOf("Apps", "Referrer", "Deep", "SV"),
                    ) { resultMap ->
                        val valuesData = resultMap.getOrDefault(
                            "SV", null
                        ) as ValuesData?

                        val toolsData = splashOperator.parseValues(
                            appsData = resultMap.getOrDefault(
                                "Apps", null
                            ) as AppsData?,
                            referrerData = resultMap.getOrDefault(
                                "Referrer", null
                            ) as ReferrerData?,
                            deepLink = resultMap.getOrDefault(
                                "Deep", null
                            ) as String?
                        )

                        val link = splashOperator.generateLink(
                            tracker = tracker,
                            policyValues = policyValues,
                            parseToolsData = toolsData,
                            valuesData = valuesData
                        )

                        splashOperator.openWeb(activity, link, false, title)

                        CoroutineScope(Dispatchers.IO).launch {
                            splashOperator.sendOneSignal(
                                push = toolsData.sL?.getOrNull(1),
                                appsId = valuesData?.appsFlyerId,
                                crypto = crypto
                            )
                        }

                    }

                    async {
                        collector.sendData("TitleData",titleData)

                        splashOperator.appsFlyerInit(
                            activity,
                            policyValues.AFK
                        ) { appsData ->
                            collector.sendData("Apps", appsData)
                        }

                        splashOperator.initReferrer(activity) { referrer ->
                            val referrerData =
                                splashOperator.getReferrerData(
                                    referrer,
                                    policyValues.FBDK,
                                    crypto = crypto
                                )

                            collector.sendData("Referrer", referrerData)
                        }

                        splashOperator.getDeepLink(
                            fbId = policyValues.FID,
                            fbToken = policyValues.FToken
                        ) { deepLink ->
                            collector.sendData("Deep", deepLink)
                        }

                        splashOperator.getSystemValues { systemValuesData ->
                            collector.sendData("SV", systemValuesData)
                        }
                    }
                }
            }
        }
    }

}