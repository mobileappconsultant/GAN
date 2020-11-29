package com.android.breakingbad

import android.app.Application
import com.android.breakingbad.di.AppComponent
import com.android.breakingbad.di.DaggerAppComponent
import com.android.breakingbad.di.module.AppModule
import com.android.breakingbad.di.module.BreakingBadApiModule
import timber.log.Timber

open class BreakingBadExplorerApplication : Application() {

    lateinit var appComponent: AppComponent

    open fun initAppComponent(app: BreakingBadExplorerApplication): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app)).breakingBadApiModule(BreakingBadApiModule())
            .build()
    }

    companion object {
        @get:Synchronized
        lateinit var application: BreakingBadExplorerApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = initAppComponent(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
