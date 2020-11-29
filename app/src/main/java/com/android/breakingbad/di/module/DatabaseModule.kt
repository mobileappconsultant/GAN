package com.android.breakingbad.di.module

import android.app.Application
import com.android.breakingbad.domain.database.AppDatabase
import com.android.breakingbad.domain.database.BreakingBadDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    fun provideHospitalDao(appDatabase: AppDatabase): BreakingBadDao {
        return appDatabase.breakingBadDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(appContext: Application): AppDatabase {
        return AppDatabase.buildDatabase(appContext)
    }
}
