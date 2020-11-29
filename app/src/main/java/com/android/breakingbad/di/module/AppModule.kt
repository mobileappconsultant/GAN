

package com.android.breakingbad.di.module

import android.app.Application
import com.android.breakingbad.domain.AppSchedulerProvider
import com.android.breakingbad.domain.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val sensyneApplication: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application = sensyneApplication

}
