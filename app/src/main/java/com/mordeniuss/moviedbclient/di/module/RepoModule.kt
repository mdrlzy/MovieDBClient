package com.mordeniuss.moviedbclient.di.module

import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieLocalDataSource
import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieRemoteDataSource
import com.mordeniuss.moviedbclient.mvp.model.network.NetworkStatus
import com.mordeniuss.moviedbclient.mvp.model.repo.MovieRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun movieRepo(
        local: MovieLocalDataSource,
        remote: MovieRemoteDataSource,
        networkStatus: NetworkStatus
    ): MovieRepo {
        return MovieRepo(local, remote, networkStatus)
    }
}