package xyz.jonthn.architectcoder

import android.app.Application
import xyz.jonthn.architectcoder.di.DaggerMyMoviesComponent
import xyz.jonthn.architectcoder.di.MyMoviesComponent

class MoviesApp : Application() {

    lateinit var component: MyMoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyMoviesComponent
            .factory()
            .create(this)
    }
}