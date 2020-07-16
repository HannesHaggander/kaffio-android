package com.towerowl.kaffio

import android.app.Application
import com.towerowl.kaffio.di.AppComponent
import com.towerowl.kaffio.di.ContextModule
import com.towerowl.kaffio.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var dagger: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        dagger = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()
    }

}