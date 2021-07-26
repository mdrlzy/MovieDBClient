package com.mordeniuss.moviedbclient.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomMovie(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val poster: String?,
    val date: Long?,
    val isLiked: Boolean
)