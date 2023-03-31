package com.policy.shower.core.util

import java.net.URLEncoder

fun encodeUrl(url: String): String {
    return try {
        URLEncoder.encode(url, "utf-8")
    } catch (e: Exception) {
        "null"
    }
}
