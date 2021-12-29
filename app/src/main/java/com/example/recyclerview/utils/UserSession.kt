package com.example.recyclerview.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


class UserSession {
    private lateinit var prefs: SharedPreferences

    fun MySession(context: Context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setUserSession(sessionID: String){
        prefs.edit().putString("sessionID", sessionID).apply()
    }

    fun getUserSession(): String{
        val sessionID: String? = prefs.getString("sessionID","")
        return sessionID.toString()
    }
}