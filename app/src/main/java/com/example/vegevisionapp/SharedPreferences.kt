package com.example.vegevisionapp

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(IS_LOGGED_IN_KEY, value)
            }
        }

    companion object {
        private const val PREFS_NAME = "AppPreferences"
        private const val IS_LOGGED_IN_KEY = "isLoggedIn"

        private var instance: AppPreferences? = null

        fun getInstance(context: Context): AppPreferences {
            if (instance == null) {
                instance = AppPreferences(context)
            }
            return instance!!
        }
    }
}
