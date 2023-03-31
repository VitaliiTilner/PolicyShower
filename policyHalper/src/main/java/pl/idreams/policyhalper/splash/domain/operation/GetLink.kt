package com.policy.shower.splash.domain.operation

class GetLink(
    private val linkDatabase: com.policy.shower.core.data.local.LinkDatabase,
){
    suspend operator fun invoke(): String? {
        return linkDatabase.getLink()
    }
}