package com.android.breakingbad

import com.android.breakingbad.domain.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulerProvider constructor(private val testScheduler: TestScheduler) :
    SchedulerProvider {
    override fun ui(): Scheduler = testScheduler
    override fun computation(): Scheduler = testScheduler
    override fun io(): Scheduler = testScheduler
}


