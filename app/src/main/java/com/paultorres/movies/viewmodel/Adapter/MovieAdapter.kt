package com.paultorres.movies.viewmodel.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.paultorres.movies.R
import com.paultorres.movies.model.local.MoviesEntity
//import com.paultorres.movies.model.api.MoviesModel
import com.paultorres.movies.databinding.ItemMoviesBinding
import com.paultorres.movies.util.POSTER_BASE_URL

//class MovieAdapter(private val onCLickListener : (MoviesModel) -> Unit) : PagingDataAdapter<MoviesModel, MovieAdapter.MovieViewHolder>(COMPARATOR) {
class MovieAdapter(private val onCLickListener: (MoviesEntity) -> Unit) :
    PagingDataAdapter<MoviesEntity, MovieAdapter.MovieViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            //holder.bind(currentItem)
            holder.bind(currentItem, onCLickListener)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //fun bind(movie: MoviesModel) {
        fun bind(movie: MoviesEntity, onCLickListener: (MoviesEntity) -> Unit) {
            with(binding) {
                Glide.with(itemView)
                    .load("$POSTER_BASE_URL${movie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMovieImg)
                tvMovieTitle.text = movie.title
                itemView.setOnClickListener { onCLickListener(movie) }
            }
        }
    }


    companion object {
        //private val COMPARATOR = object : DiffUtil.ItemCallback<MoviesModel>() {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MoviesEntity>() {
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean =
                oldItem == newItem
        }
    }

}