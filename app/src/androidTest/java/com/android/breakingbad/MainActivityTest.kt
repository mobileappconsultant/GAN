package com.android.breakingbad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.breakingbad.presentation.BreakingBadViewModel
import com.android.breakingbad.ui.activities.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var mainViewModel: BreakingBadViewModel

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    open fun setUp() {
        BreakingBadTestApplication.testAppComponent.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loadingIdlingResource: LoadingIdlingResource

    @Test
    fun appLaunchesSuccessfully() {
        val mainActivityScenario = activityRule.scenario
        mainActivityScenario.onActivity { activity ->
            loadingIdlingResource =
                LoadingIdlingResource(activity)
            IdlingRegistry.getInstance().register(loadingIdlingResource)
            BreakingBadTestApplication.testAppComponent.inject(activity)

            mainViewModel =
                ViewModelProvider(activity, viewModelFactory).get(BreakingBadViewModel::class.java)
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(loadingIdlingResource)
    }
}
