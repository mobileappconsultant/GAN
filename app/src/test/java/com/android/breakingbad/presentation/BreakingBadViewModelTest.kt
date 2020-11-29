package com.android.breakingbad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.android.breakingbad.common.RoomRepository
import com.android.breakingbad.common.TaskResult
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import com.android.breakingbad.domain.repository.BreakingItemsQueryRepository
import com.android.breakingbad.presentation.BreakingBadViewModel
import com.android.breakingbad.presentation.LoadingState
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class BreakingBadViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    var breakingBadRepository: BreakingItemsQueryRepository =
        Mockito.mock(BreakingItemsQueryRepository::class.java)

    @Mock
    var taskRepository = Mockito.mock(RoomRepository::class.java)

    private var viewModel: BreakingBadViewModel? = null
    var mTestScheduler = io.reactivex.rxjava3.schedulers.TestScheduler()
    var schedulerProvider: TestSchedulerProvider = TestSchedulerProvider(mTestScheduler)

    @Mock
    var actorDataListObserver: Observer<TaskResult<List<BreakbadCharacterRoomItem>>> =
        Mockito.mock(Observer::class.java) as Observer<TaskResult<List<BreakbadCharacterRoomItem>>>


    @Mock
    var loadingStateObserver: Observer<LoadingState> =
        Mockito.mock(Observer::class.java) as Observer<LoadingState>


    @Mock
    var lifecycleOwner: LifecycleOwner = Mockito.mock(LifecycleOwner::class.java)

    @Mock
    var lifecycle: Lifecycle = Mockito.mock(Lifecycle::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner);
        viewModel = BreakingBadViewModel(
            breakingBadRepository!!,
            taskRepository!!
        )
        viewModel!!.schedulerProvider = schedulerProvider
        viewModel?._actorListStateLiveData?.observeForever(actorDataListObserver!!)
        viewModel?._actorsLoadingStateLiveData?.observeForever(loadingStateObserver!!);
    }

    @Test
    fun testNullObserver() {
        Mockito.`when`(breakingBadRepository?.retrieveAllActorsFromRemoteSource()).thenReturn(null)
        TestCase.assertNotNull(viewModel?._actorListStateLiveData)
        TestCase.assertTrue(viewModel?._actorListStateLiveData?.hasObservers()!!)
    }


    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        var listOfActors = BreakingBadDataFactory.makeMultipleActorDataItems(10)
        var breakingBadPayload = BreakingBadPayLoad()
        breakingBadPayload.list = listOfActors.toMutableList()

        // given
        var response = io.reactivex.rxjava3.core.Single.just(Response.success(breakingBadPayload))
        Mockito.`when`(breakingBadRepository?.retrieveAllActorsFromRemoteSource()).thenReturn(
            response
        )
        //when
        Mockito.`when`(taskRepository.insertActors(listOfActors)).thenReturn(Completable.complete())

        //then
        viewModel?.fetchBreakingBadData()
        assertNotNull(viewModel?._actorListStateLiveData)
        assertEquals(breakingBadPayload.list.size, 10)

    }


    @Test
    fun givenServerResponse400_whenFetch_shouldReturnFail() {

        var breakingBadPayload = BreakingBadPayLoad()


        // given
        Mockito.`when`(breakingBadRepository?.retrieveAllActorsFromRemoteSource()).thenReturn(Single.error(Throwable()))
        //when


        //then
        viewModel?.fetchBreakingBadData()
        assertNotNull(viewModel?._actorListStateLiveData)
        assertEquals(breakingBadPayload.list.size, 0)

    }

    companion object {
        @BeforeClass
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        fun tearDownClass() {
            RxAndroidPlugins.reset()
        }
    }
}