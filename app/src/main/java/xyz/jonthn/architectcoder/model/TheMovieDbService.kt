package xyz.jonthn.architectcoder.model

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.jonthn.architectcoder.model.MovieDbResult

interface TheMovieDbService {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDbResult
}