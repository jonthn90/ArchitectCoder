package xyz.jonthn.architectcoder.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.jonthn.architectcoder.model.database.Movie
import xyz.jonthn.architectcoder.model.database.MovieDao

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}