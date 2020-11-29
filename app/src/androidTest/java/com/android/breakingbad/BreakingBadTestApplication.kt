package com.android.breakingbad

import com.android.breakingbad.di.AppComponent
import com.android.breakingbad.di.module.AppModule

class BreakingBadTestApplication : BreakingBadExplorerApplication() {

    override fun initAppComponent(app: BreakingBadExplorerApplication): AppComponent {
        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(app))
            .testBreakingBadApiModule(TestBreakingBadApiModule(this)).build()
        return testAppComponent
    }

    companion object {
        lateinit var testAppComponent: TestAppComponent
    }
}
