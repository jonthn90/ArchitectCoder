package xyz.jonthn.data.repository

import xyz.jonthn.data.source.LocalDataSource
import xyz.jonthn.data.source.RemoteDataSource
import xyz.jonthn.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDatasource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {

    suspend fun getPopularMovies(): List<Movie> {

        if (localDataSource.isEmpty()) {
            val movies =
                remoteDatasource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }

        return localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}