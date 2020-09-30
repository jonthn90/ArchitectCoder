package xyz.jonthn.architectcoder

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.jonthn.architectcoder.data.AndroidPermissionChecker
import xyz.jonthn.architectcoder.data.PlayServicesLocationDataSource
import xyz.jonthn.architectcoder.data.database.MovieDatabase
import xyz.jonthn.architectcoder.data.database.RoomDataSource
import xyz.jonthn.architectcoder.data.server.TheMovieDbDataSource
import xyz.jonthn.architectcoder.ui.detail.DetailFragment
import xyz.jonthn.architectcoder.ui.detail.DetailViewModel
import xyz.jonthn.architectcoder.ui.main.MainFragment
import xyz.jonthn.architectcoder.ui.main.MainViewModel
import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.data.repository.PermissionChecker
import xyz.jonthn.data.repository.RegionRepository
import xyz.jonthn.data.source.LocalDataSource
import xyz.jonthn.data.source.LocationDataSource
import xyz.jonthn.data.source.RemoteDataSource
import xyz.jonthn.usescases.FindMovieById
import xyz.jonthn.usescases.GetPopularMovies
import xyz.jonthn.usescases.ToggleMovieFavorite

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}