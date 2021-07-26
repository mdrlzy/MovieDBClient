package com.mordeniuss.moviedbclient.di.module

import com.mordeniuss.moviedbclient.mvp.model.api.MovieDBApi
import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieLocalDataSource
import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieRemoteDataSource
import com.mordeniuss.moviedbclient.mvp.model.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun repositoryLocal(db: Database): MovieLocalDataSource {
        return MovieLocalDataSource(db.movieDao())
    }

    @Singleton
    @Provides
    fun repositoryRemote(api: MovieDBApi): MovieRemoteDataSource {
        return MovieRemoteDataSource(api)
    }
}