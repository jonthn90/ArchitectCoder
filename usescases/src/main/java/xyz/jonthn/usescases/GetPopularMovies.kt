package xyz.jonthn.usescases

import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}
