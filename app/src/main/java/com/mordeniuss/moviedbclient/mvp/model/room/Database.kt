package com.mordeniuss.moviedbclient.mvp.model.room

import androidx.room.RoomDatabase
import com.mordeniuss.moviedbclient.mvp.model.entity.room.RoomMovie
import com.mordeniuss.moviedbclient.mvp.model.room.dao.MovieDao

@androidx.room.Database(
    entities = [
        RoomMovie::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_NAME = "database.db"
    }
}