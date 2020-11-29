package com.android.breakingbad.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.breakingbad.R

class BreakingBadDetailFragment : Fragment(R.layout.fragment_hospital_details) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseUIElements()
    }

    private fun initialiseUIElements() {
        arguments?.let {
            var currentBreakingBadCharacter = BreakingBadDetailFragmentArgs.fromBundle(it)
            currentBreakingBadCharacter
            // hospitalNameTextView.text = currentBreakingBadCharacter.currentHospital
        }
    }
}
