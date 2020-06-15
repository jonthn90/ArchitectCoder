package xyz.jonthn.architectcoder.di

import dagger.Module
import dagger.Provides
import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.data.repository.PermissionChecker
import xyz.jonthn.data.repository.RegionRepository
import xyz.jonthn.data.source.LocalDataSource
import xyz.jonthn.data.source.LocationDataSource
import xyz.jonthn.data.source.RemoteDataSource
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}