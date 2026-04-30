package com.example.ascentlister

import android.app.Application
import com.example.ascentlister.di.initKoin
import org.koin.android.ext.koin.androidContext

class AscentListerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AscentListerApplication)
        }
    }
}