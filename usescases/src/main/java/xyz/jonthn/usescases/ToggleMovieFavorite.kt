package xyz.jonthn.usescases

import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie) = moviesRepository.update(movie)
}