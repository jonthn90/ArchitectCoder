package xyz.jonthn.architectcoder.data.server

import xyz.jonthn.architectcoder.data.toDomainMovie
import xyz.jonthn.data.source.RemoteDataSource
import xyz.jonthn.domain.Movie

class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}