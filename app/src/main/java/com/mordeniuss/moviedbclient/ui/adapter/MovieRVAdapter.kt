package com.mordeniuss.moviedbclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mordeniuss.moviedbclient.R
import com.mordeniuss.moviedbclient.mvp.presenter.list.IMovieListPresenter
import com.mordeniuss.moviedbclient.mvp.view.item.MovieItemView
import com.mordeniuss.moviedbclient.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieRVAdapter(
    val presenter: IMovieListPresenter
): RecyclerView.Adapter<MovieRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent,
            false
        )
    )

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
        with(holder.containerView) {
            iv_like.setOnClickListener {
                presenter.onLikeBtnClicked(holder)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, MovieItemView {

        override var pos = -1

        override fun setTitle(title: String) = with(containerView) {
            tv_title.text = title
        }

        override fun setDescription(description: String) = with(containerView) {
            tv_description.text = description
        }

        override fun setPoster(url: String) = with(containerView) {
            loadImage(url, iv_poster)
        }

        override fun setLiked(isLiked: Boolean) = with(containerView) {
            if (isLiked)
                iv_like.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, context.theme))
            else
                iv_like.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_border, context.theme))

        }

        override fun setDate(date: String) = with(containerView) {
            tv_date.text = date
        }
    }
}