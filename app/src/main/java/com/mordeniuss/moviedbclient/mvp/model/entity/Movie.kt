package com.mordeniuss.moviedbclient.mvp.model.entity

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*


data class Movie(
    var id: Long,
    val title: String,
    val description: String,
    val poster: String?,
    val date: DateTime?,
    var isLiked: Boolean
) {
    fun dateFormatted(): String? {
        val formatter = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale.getDefault())
        return date?.let {formatter.print(it)}
    }
}