package xyz.jonthn.architectcoder

import android.app.Application

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }
}