package xyz.jonthn.architectcoder.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
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
import xyz.jonthn.usescases.FindMovieById
import xyz.jonthn.usescases.ToggleMovieFavorite

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<Movie>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observing LiveData finds the movie`() {
        vm.movie.observeForever(observer)

        verify(observer).onChanged(vm.movie.value)
    }

    @Test
    fun `favorite is updated in local data source`() {
        vm.movie.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(1).favorite)
        }
    }
}