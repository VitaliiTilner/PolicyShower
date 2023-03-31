package com.policy.shower.web.opearator

import com.policy.shower.core.data.local.LinkDatabase

class SaveLink(
    private val linkDatabase: LinkDatabase
) {
    suspend operator fun invoke(link : String){
        return linkDatabase.saveLink(link)
    }
}