package com.android.breakingbad.di

import com.android.breakingbad.di.module.AppModule
import com.android.breakingbad.di.module.DatabaseModule
import com.android.breakingbad.di.module.BreakingBadApiModule
import com.android.breakingbad.di.module.ViewModelModule
import com.android.breakingbad.ui.activities.MainActivity
import com.android.breakingbad.ui.fragments.BreakingBadListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, BreakingBadApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(hospitalListFragment: BreakingBadListFragment)
}
