package xyz.jonthn.architectcoder.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import xyz.jonthn.architectcoder.ui.FakeLocalDataSource
import xyz.jonthn.architectcoder.ui.defaultFakeMovies
import xyz.jonthn.architectcoder.ui.initMockedDi
import xyz.jonthn.data.source.LocalDataSource
import xyz.jonthn.domain.Movie
import xyz.jonthn.testshared.mockedMovie
import xyz.jonthn.usescases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<Movie>>

    private lateinit var vm: MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(Dispatchers.Unconfined)

        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.movies.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(defaultFakeMovies)
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.movies.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(fakeLocalMovies)
    }
}