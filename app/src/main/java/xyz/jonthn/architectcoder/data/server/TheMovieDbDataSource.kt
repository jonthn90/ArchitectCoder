package xyz.jonthn.architectcoder.data.server

import xyz.jonthn.architectcoder.data.toDomainMovie
import xyz.jonthn.data.source.RemoteDataSource
import xyz.jonthn.domain.Movie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}