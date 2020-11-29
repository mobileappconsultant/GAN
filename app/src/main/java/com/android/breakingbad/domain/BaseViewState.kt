package com.android.breakingbad.domain

open class BaseViewState<T> {
    var data: T? = null
        protected set
    var error: Throwable? = null
    var currentState = 0

}