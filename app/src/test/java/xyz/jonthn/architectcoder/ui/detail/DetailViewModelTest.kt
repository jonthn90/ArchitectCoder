package xyz.jonthn.architectcoder.ui.detail

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import xyz.jonthn.domain.Movie
import xyz.jonthn.testshared.mockedMovie
import xyz.jonthn.usescases.FindMovieById
import xyz.jonthn.usescases.ToggleMovieFavorite

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var observer: Observer<Movie>

    private lateinit var vm: DetailViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(Dispatchers.Unconfined)

        vm = DetailViewModel(1, findMovieById, toggleMovieFavorite, Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observing LiveData finds the movie`() {

        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            whenever(findMovieById.invoke(1)).thenReturn(movie)

            vm.movie.observeForever(observer)

            verify(observer).onChanged(vm.movie.value)
        }
    }

    @Test
    fun `when favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {

            val movie = mockedMovie.copy(id = 1)

            whenever(findMovieById.invoke(1)).thenReturn(movie)

            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.movie.observeForever(observer)

            vm.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }
    }
}