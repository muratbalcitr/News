package com.m2lifeapps.challenge.core.platform

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
     override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        injectMultiDex()
    }

    private fun injectMultiDex() {
        MultiDex.install(this)
    }

    companion object {
        lateinit var appContext: Context
        val firebaseAnalytics by lazy {
            FirebaseAnalytics.getInstance(appContext)
        }

    }
}
