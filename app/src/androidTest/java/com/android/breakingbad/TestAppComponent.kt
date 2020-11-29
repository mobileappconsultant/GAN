package com.android.breakingbad

import com.android.breakingbad.di.AppComponent
import com.android.breakingbad.di.module.AppModule
import com.android.breakingbad.di.module.DatabaseModule
import com.android.breakingbad.di.module.ViewModelModule
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, TestBreakingBadApiModule::class, ViewModelModule::class])
interface TestAppComponent : AppComponent {
    fun mockWebServer(): MockWebServer
    fun inject(mainActivityTest: MainActivityTest)
}
