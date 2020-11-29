package com.android.breakingbad.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.breakingbad.R
import com.android.breakingbad.BreakingBadExplorerApplication
import com.android.breakingbad.presentation.BreakingBadViewModel
import javax.inject.Inject

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: BreakingBadViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BreakingBadExplorerApplication.application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(BreakingBadViewModel::class.java)
    }

}
