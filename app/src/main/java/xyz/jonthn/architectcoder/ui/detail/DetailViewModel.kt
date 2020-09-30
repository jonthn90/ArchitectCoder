package xyz.jonthn.architectcoder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import xyz.jonthn.architectcoder.ui.common.ScopedViewModel
import xyz.jonthn.domain.Movie
import xyz.jonthn.usescases.FindMovieById
import xyz.jonthn.usescases.ToggleMovieFavorite

class DetailViewModel(
    private val movieId: Int, private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    init {
        launch {
            _movie.value = findMovieById.invoke(movieId)
        }
    }

    fun onFavoriteClicked() {
        launch {
            movie.value?.let {
                _movie.value = toggleMovieFavorite.invoke(it)
            }
        }
    }
}