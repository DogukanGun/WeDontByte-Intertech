package com.example.intertech_account

import android.app.Application
import android.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        var change = ""
        val sharedPreferences =getSharedPreferences("SettingsActivity", MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "English")
        if (language == "Turkish") {
            change="tr"
        } else if (language=="English" ) {
            change = "en"
        }else {
            change =""
        }

        BaseActivity.dLocale = Locale(change) //set any locale you want here
    }
}