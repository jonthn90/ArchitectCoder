package xyz.jonthn.data.source

import xyz.jonthn.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}