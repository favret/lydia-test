package com.mellonmellon.lydiausers

import android.app.Application
import android.content.Context

class LydiaUserApp: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}