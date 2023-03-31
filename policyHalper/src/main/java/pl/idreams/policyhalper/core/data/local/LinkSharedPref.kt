package com.policy.shower.core.data.local

import android.content.Context
import android.content.SharedPreferences
import com.policy.shower.core.data.local.LinkDatabase

class LinkSharedPref(
    private val context: Context
    ) : LinkDatabase {
    override suspend fun saveLink(link: String) {
        val sharedPref = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor  = sharedPref.edit()

        editor.putString("LData",link)

        editor.apply()
    }

    override suspend fun getLink(): String? {
        val sharedPref = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)

        return sharedPref.getString("LData",null)
    }

}