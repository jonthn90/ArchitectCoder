package xyz.jonthn.architectcoder.ui.main

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.usescases.GetPopularMovies

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MainViewModel(getPopularMovies)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}