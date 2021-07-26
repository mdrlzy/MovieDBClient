package com.mordeniuss.moviedbclient.di

import com.mordeniuss.moviedbclient.di.module.*
import com.mordeniuss.moviedbclient.ui.activity.MainActivity
import com.mordeniuss.moviedbclient.mvp.presenter.MainPresenter
import com.mordeniuss.moviedbclient.mvp.presenter.SearchPresenter
import com.mordeniuss.moviedbclient.ui.fragment.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CiceroneModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(searchFragment: SearchFragment)
    fun inject(searchPresenter: SearchPresenter)
}