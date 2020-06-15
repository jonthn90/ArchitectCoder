package xyz.jonthn.architectcoder.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.data.AndroidPermissionChecker
import xyz.jonthn.architectcoder.data.PlayServicesLocationDataSource
import xyz.jonthn.architectcoder.data.database.MovieDatabase
import xyz.jonthn.architectcoder.data.database.RoomDataSource
import xyz.jonthn.architectcoder.data.server.TheMovieDbDataSource
import xyz.jonthn.data.repository.PermissionChecker
import xyz.jonthn.data.source.LocalDataSource
import xyz.jonthn.data.source.LocationDataSource
import xyz.jonthn.data.source.RemoteDataSource
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}