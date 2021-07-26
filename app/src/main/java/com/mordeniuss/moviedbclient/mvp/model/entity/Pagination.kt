package com.mordeniuss.moviedbclient.mvp.model.entity

data class Pagination<T>(
    val page: Int,
    var results: List<T>
)