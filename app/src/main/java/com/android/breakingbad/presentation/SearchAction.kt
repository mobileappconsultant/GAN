package com.android.breakingbad.presentation

sealed class SearchAction {
    class UserTypingAction(val searchString: String?) : SearchAction()

    class NoSearchStringAction : SearchAction()
}
