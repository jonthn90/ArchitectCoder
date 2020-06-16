package xyz.jonthn.usescases

import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}