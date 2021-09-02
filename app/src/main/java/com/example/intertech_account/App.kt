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
        val sharedPreferences =getSharedPreferences(getString(R.string.settings_activity), MODE_PRIVATE)
        val language = sharedPreferences.getString(getString(R.string.language), getString(R.string.english))
        if (language == getString(R.string.turkish)) {
            change="tr"
        } else if (language=="English" ) {
            change = "en"
        }else {
            change =""
        }

        BaseActivity.dLocale = Locale(change) //set any locale you want here
    }
}