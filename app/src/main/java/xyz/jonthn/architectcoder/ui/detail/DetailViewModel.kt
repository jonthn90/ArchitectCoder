package xyz.jonthn.architectcoder.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import xyz.jonthn.architectcoder.model.database.Movie
import xyz.jonthn.architectcoder.model.server.MoviesRepository
import xyz.jonthn.architectcoder.ui.common.ScopedViewModel

class DetailViewModel(private val movieId: Int, private val moviesRepository: MoviesRepository) :
    ScopedViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    init {
        launch {
            _movie.value = moviesRepository.findById(movieId)
            updateUi()
        }
    }

    fun onFavoriteClicked() {
        launch {
            movie.value?.let {
                val updatedMovie = it.copy(favorite = !it.favorite)
                _movie.value = updatedMovie
                updateUi()
                moviesRepository.update(updatedMovie)
            }
        }
    }

    private fun updateUi() {
        movie.value?.run {
            _title.value = title
            _overview.value = overview
            _url.value = backdropPath
            _favorite.value = favorite
        }
    }
}