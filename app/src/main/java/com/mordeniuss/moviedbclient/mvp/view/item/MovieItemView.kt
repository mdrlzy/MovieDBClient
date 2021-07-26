package com.mordeniuss.moviedbclient.mvp.view.item

interface MovieItemView {
    var pos: Int

    fun setTitle(title: String)
    fun setDescription(description: String)
    fun setPoster(url: String)
    fun setLiked(isLiked: Boolean)
    fun setDate(date: String)
}