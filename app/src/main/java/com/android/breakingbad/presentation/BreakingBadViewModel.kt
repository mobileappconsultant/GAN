package com.android.breakingbad.presentation

import androidx.lifecycle.*
import com.android.breakingbad.common.RoomRepository
import com.android.breakingbad.common.TaskResult
import com.android.breakingbad.domain.AppSchedulerProvider
import com.android.breakingbad.domain.SchedulerProvider
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.repository.BreakingItemsQueryRepository
import com.android.breakingbad.presentation.adapters.ClickActions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreakingBadViewModel @Inject constructor(
    private val repository: BreakingItemsQueryRepository,
    private val taskRepository: RoomRepository
) : ViewModel() {
    var schedulerProvider: SchedulerProvider = AppSchedulerProvider()
    private var disposables: CompositeDisposable = CompositeDisposable()
    val _actorsLoadingStateLiveData = MutableLiveData<LoadingState>()
    val _actorListStateLiveData = MutableLiveData<TaskResult<List<BreakbadCharacterRoomItem>>>()
    val _actorSeasonListLiveData = MutableLiveData<List<Int>>()
    private var hashSetSeasons: HashSet<Int> = hashSetOf()

    val breakingBadListMediator = MediatorLiveData<TaskResult<List<BreakbadCharacterRoomItem>>>()
    val loadingMediator = MediatorLiveData<LoadingState>()
    val actorSeasonListMediatorLiveData = MediatorLiveData<List<Int>>()
    private val _navigateToDetails = MutableLiveData<Event<ClickActions>>()
    val navigateToDetails: LiveData<Event<ClickActions>>
        get() = _navigateToDetails

    init {

        breakingBadListMediator.addSource(_actorListStateLiveData) {
            breakingBadListMediator.value = it
        }
        loadingMediator.addSource(_actorsLoadingStateLiveData) {
            loadingMediator.value = it
        }
        actorSeasonListMediatorLiveData.addSource(_actorSeasonListLiveData) {
            actorSeasonListMediatorLiveData.value = it
        }
    }

    fun onFragmentReady() {
        fetchBreakingBadData()
    }

    fun fetchBreakingBadData() {

        var disposable = repository.retrieveAllActorsFromRemoteSource()
            .doOnSubscribe { onLoading() }
            .doOnEvent { breakingBadPayload, throwable -> onLoading() }
            .map { breakingPayPayload ->
                var breakingBadPayLoadList = breakingPayPayload.body()?.list
                taskRepository.insertActors(breakingBadPayLoadList!!).subscribe()
                getSeasons(breakingBadPayLoadList)
            }
            .flatMapObservable { taskRepository.getAllActors() }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ breakingBadPayLoadList ->
                onLoaded()
                onSeasonDataLoaded()
                onActorDataListLoaded(breakingBadPayLoadList)
            }, {
                _actorListStateLiveData.postValue(TaskResult.Error(Exception(it.message)))

            })
        disposables.add(disposable)
    }


    private fun onLoading() {
        viewModelScope.launch(Dispatchers.Main) {
            _actorsLoadingStateLiveData.value = LoadingState.LOADING
        }
    }

    private fun onSeasonDataLoaded() {
        viewModelScope.launch(Dispatchers.Main) {
            _actorSeasonListLiveData.postValue(hashSetSeasons.toList())
        }
    }

    private fun onActorDataListLoaded(breakingBadPayLoadList: List<BreakbadCharacterRoomItem>) {
        viewModelScope.launch(Dispatchers.Main) {
            _actorListStateLiveData.postValue(TaskResult.Success(breakingBadPayLoadList))
        }

    }

    private fun onLoaded() {
        viewModelScope.launch(Dispatchers.Main) {
            _actorsLoadingStateLiveData.value = LoadingState.LOADED
        }
    }

    private fun getSeasons(list: MutableList<BreakingBadDataItem>) {
        list.filter { !it.appearance.isNullOrEmpty() }.map {
            hashSetSeasons.addAll(
                it.appearance!!
            )
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposables?.let {
            if (!it.isDisposed) it.clear()
        }
    }

    fun onActorClicked(clickActions: ClickActions) {
        _navigateToDetails.value = Event(clickActions)
    }


    fun onActorSearchBySeason(season: Int) {

        var disposable = taskRepository.queryActorsBySeason(season)
            .doOnSubscribe { onLoading() }

            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ breakingBadPayLoadList ->
                onLoaded()
                onSeasonDataLoaded()
                onActorDataListLoaded(breakingBadPayLoadList)
            }, {
                _actorListStateLiveData.postValue(TaskResult.Error(Exception(it.message)))

            })
        disposables.add(disposable)
    }


    fun onActorSearchByName(actorName: String) {

        var disposable = taskRepository.queryActorsByName(actorName)
            .doOnSubscribe { onLoading() }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ breakingBadPayLoadList ->
                onLoaded()
                onSeasonDataLoaded()
                onActorDataListLoaded(breakingBadPayLoadList)
            }, {
                _actorListStateLiveData.postValue(TaskResult.Error(Exception(it.message)))

            })
        disposables.add(disposable)
    }


}