package com.mordeniuss.moviedbclient.mvp.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mordeniuss.moviedbclient.mvp.model.entity.room.RoomMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomMovie)

    @Query("SELECT * FROM RoomMovie WHERE id = :id")
    fun getById(id: Long): RoomMovie?
}