package com.mordeniuss.moviedbclient.ui

import android.app.Application
import com.github.moxy_community.moxy.androidx.BuildConfig
import com.mordeniuss.moviedbclient.di.module.AppModule
import com.mordeniuss.moviedbclient.di.AppComponent
import com.mordeniuss.moviedbclient.di.DaggerAppComponent
import timber.log.Timber

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set


    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}