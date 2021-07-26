package com.mordeniuss.moviedbclient.mvp.model.entity.api

data class ResponsePagination <T> (
    val page: Int,
    val results: List<T>
)