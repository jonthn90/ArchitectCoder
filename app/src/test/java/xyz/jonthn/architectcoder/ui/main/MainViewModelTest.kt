package xyz.jonthn.architectcoder.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import xyz.jonthn.architectcoder.ui.common.Event
import xyz.jonthn.domain.Movie
import xyz.jonthn.testshared.mockedMovie
import xyz.jonthn.usescases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerRequestLocationPermission: Observer<Event<Unit>>

    @Mock
    lateinit var observerMovies: Observer<List<Movie>>

    private lateinit var vm: MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(Dispatchers.Unconfined)

        vm = MainViewModel(getPopularMovies, Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.requestLocationPermission.observeForever(observerRequestLocationPermission)

        verify(observerRequestLocationPermission).onChanged(vm.requestLocationPermission.value)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(id = 1))

            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.loading.observeForever(observerLoading)

            vm.onCoarsePermissionRequested()

            verify(observerLoading).onChanged(vm.loading.value)
        }
    }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.movies.observeForever(observerMovies)

            vm.onCoarsePermissionRequested()

            verify(observerMovies).onChanged(vm.movies.value)
        }
    }
}