package com.mordeniuss.moviedbclient.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.mordeniuss.moviedbclient.R

fun loadImage(url: String, container: ImageView) {
    Glide.with(container.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .priority(Priority.IMMEDIATE)
        .into(container)
}
