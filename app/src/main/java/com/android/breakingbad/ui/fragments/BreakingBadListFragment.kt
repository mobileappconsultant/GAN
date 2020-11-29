package com.android.breakingbad.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.breakingbad.BreakingBadExplorerApplication.Companion.application
import com.android.breakingbad.R
import com.android.breakingbad.common.ConnectivityLiveData
import com.android.breakingbad.common.EndlessRecyclerOnScrollListener
import com.android.breakingbad.common.TaskResult
import com.android.breakingbad.presentation.BreakingBadViewModel
import com.android.breakingbad.presentation.LoadingState
import com.android.breakingbad.presentation.adapters.BreakingBadItemsAdapter
import com.android.breakingbad.presentation.adapters.CheckBoxAdapter
import com.android.breakingbad.presentation.adapters.ClickActions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_actor_list.*
import kotlinx.android.synthetic.main.search_view.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class BreakingBadListFragment : Fragment(R.layout.fragment_actor_list) {

    private lateinit var mainViewModel: BreakingBadViewModel
    private lateinit var breakingbadListAdapter: BreakingBadItemsAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var isLoading: Boolean = false
    private val debouncePeriod: Long = 500
    private var isSearching = false
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val listOfSeasons = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        connectivityLiveData = ConnectivityLiveData(application)
        application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            BreakingBadViewModel::class.java
        )
    }

    private fun initialiseScrollListener() {
        breakingbadRecyclerView.addOnScrollListener(
            object : EndlessRecyclerOnScrollListener() {
                override fun onLoadMore() {
                    if (isSearching) return
                    isLoading = true
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseObservers()
        initialiseUIElements()
        initialiseScrollListener()
    }

    private fun initialiseObservers() {
        mainViewModel.breakingBadListMediator.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is TaskResult.Success -> {
                        breakingbadListAdapter.updateData(it.data)
                    }
                    is TaskResult.Error -> {
                        Toast.makeText(context, "There's an error", Toast.LENGTH_LONG).show()
                    }
                }

            }
        )

        mainViewModel.actorSeasonListMediatorLiveData.observe(
            viewLifecycleOwner,
            {
                listOfSeasons.addAll(it)
            }
        )

        mainViewModel.loadingMediator.observe(
            viewLifecycleOwner,
            {
                onHospitalLoadingState(it)
            }
        )

        connectivityLiveData.observe(
            viewLifecycleOwner,
            { isAvailable ->
                when (isAvailable) {
                    true -> {
                        mainViewModel.onFragmentReady()
                        statusButton?.visibility = View.GONE
                        breakingbadRecyclerView?.visibility = View.VISIBLE
                    }
                    false -> {
                        statusButton?.visibility = View.VISIBLE
                        breakingbadRecyclerView?.visibility = View.GONE
                    }
                }
            }
        )

        mainViewModel.navigateToDetails.observe(
            viewLifecycleOwner,
            {
                it?.getContentIfNotHandled()?.let { clickAction ->

                    mainViewModel.onActorClicked(clickAction)
                }
            }
        )
    }




    private fun initialiseUIElements() {
        breakingbadListAdapter = BreakingBadItemsAdapter(requireContext(), { breakingBadItem ->
            mainViewModel.onActorClicked(breakingBadItem)
        })
        breakingbadRecyclerView.apply {
            adapter = breakingbadListAdapter
            hasFixedSize()
        }
    }

    private fun onHospitalLoadingState(state: LoadingState) {
        when (state) {
            LoadingState.LOADING -> {
                statusButton?.visibility = View.GONE
                breakingbadRecyclerView?.visibility = View.GONE
                loadingProgressBar?.visibility = View.VISIBLE
            }
            LoadingState.LOADED -> {
                connectivityLiveData.value?.let {
                    if (it) {
                        statusButton?.visibility = View.GONE
                        breakingbadRecyclerView?.visibility = View.VISIBLE
                    } else {
                        statusButton?.visibility = View.VISIBLE
                        breakingbadRecyclerView?.visibility = View.GONE
                    }
                }
                loadingProgressBar?.visibility = View.GONE
            }
            LoadingState.ERROR -> {
                statusButton?.visibility = View.VISIBLE
                context?.let {
                    statusButton?.setCompoundDrawables(
                        null,
                        ContextCompat.getDrawable(it, R.drawable.no_internet),
                        null,
                        null
                    )
                }
                breakingbadRecyclerView?.visibility = View.GONE
                loadingProgressBar?.visibility = View.GONE
            }
            LoadingState.UNkNOWN_ERROR -> {
                statusButton?.visibility = View.VISIBLE
                statusButton?.text = getString(R.string.unknown_error_key)
                statusButton?.setCompoundDrawables(null, null, null, null)
                breakingbadRecyclerView?.visibility = View.GONE
                loadingProgressBar?.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.searchItem -> {
                openDialog()
                return false
            }
        }
        return false
    }

    private fun openDialog() {
        val view: View = layoutInflater.inflate(R.layout.search_view, null)
        var dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        renderSearchComponentView(view)
        dialog.show()
    }

    private fun renderSearchComponentView(view: View) {
        val disposable = view.textfield_name.afterTextChangeEvents().skipInitialValue()
            .debounce(debouncePeriod, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.view.text.toString().length > 2) {
                    isSearching = true
                    mainViewModel.onActorSearchByName(it.toString())
                } else {
                    isSearching = false
                    mainViewModel.onFragmentReady()
                }
            }
          compositeDisposable.add(disposable)

        var checkBoxAdapter = CheckBoxAdapter({ clickAtion ->
            mainViewModel.onActorSearchBySeason((clickAtion as ClickActions.CheckboxClickAction).checkBoxPosition)
        })
        view.season_choice.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.season_choice.adapter = checkBoxAdapter
        checkBoxAdapter.updateData(listOfSeasons)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}
