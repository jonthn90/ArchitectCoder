package xyz.jonthn.usescases

import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}