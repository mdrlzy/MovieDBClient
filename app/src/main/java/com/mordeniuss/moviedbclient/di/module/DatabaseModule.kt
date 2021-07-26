package com.mordeniuss.moviedbclient.di.module

import androidx.room.Room
import com.mordeniuss.moviedbclient.mvp.model.room.Database
import com.mordeniuss.moviedbclient.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(app: App): Database {
        return Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}