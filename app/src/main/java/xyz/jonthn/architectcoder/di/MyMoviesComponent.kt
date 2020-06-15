package xyz.jonthn.architectcoder.di

import android.app.Application

import dagger.BindsInstance
import dagger.Component
import xyz.jonthn.architectcoder.ui.detail.DetailActivityComponent
import xyz.jonthn.architectcoder.ui.detail.DetailActivityModule
import xyz.jonthn.architectcoder.ui.main.MainActivityComponent
import xyz.jonthn.architectcoder.ui.main.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyMoviesComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule): DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}