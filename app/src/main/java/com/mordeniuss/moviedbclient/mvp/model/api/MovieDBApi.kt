package com.mordeniuss.moviedbclient.mvp.model.api

import com.mordeniuss.moviedbclient.mvp.model.entity.api.ResponseMovie
import com.mordeniuss.moviedbclient.mvp.model.entity.api.ResponsePagination
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieDBApi {

    @GET("3/search/movie?api_key=$API_KEY")
    fun searchRepositories(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<ResponsePagination<ResponseMovie>>
}

const val API_KEY = "6ccd72a2a8fc239b13f209408fc31c33"