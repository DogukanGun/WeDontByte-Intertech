package com.example.intertech_account

 import android.content.Context
 import androidx.multidex.MultiDex
 import com.zeugmasolutions.localehelper.LocaleAwareApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: LocaleAwareApplication() {

    override fun onCreate() {
        super.onCreate()


    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}