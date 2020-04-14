package xyz.jonthn.architectcoder

import android.app.Application
import androidx.room.Room
import xyz.jonthn.architectcoder.model.database.MovieDatabase

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}