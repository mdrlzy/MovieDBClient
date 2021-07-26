package com.mordeniuss.moviedbclient.mvp.model.entity.api

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

data class ResponseMovie(
    val id: Long,
    val poster_path: String?,
    val title: String,
    val overview: String,
    val release_date: String
) {
    fun resolvePosterUrl(): String? {
        return poster_path?.let {"https://image.tmdb.org/t/p/original$it"}
    }

    fun resolveDate(): DateTime? {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
        return if (release_date.isNotEmpty()) DateTime.parse(release_date, formatter) else null
    }
}