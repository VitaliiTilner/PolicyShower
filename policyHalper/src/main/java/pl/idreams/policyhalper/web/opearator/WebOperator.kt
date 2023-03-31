package com.policy.shower.web.opearator

data class WebOperator(
    val createFile: CreateFile,
    val setWebSettings: SetWebSettings,
    val saveLink : SaveLink,
    val handleOnBackPressed: HandleOnBackPressed,
)