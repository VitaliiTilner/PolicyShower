package com.policy.shower.splash.domain.operation

class ValidLink {
    operator fun invoke(link : String?) : Boolean{
        return link != null && link != "" && link != "null"
    }
}