package com.example.recyclerview.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.pages.MovieDetails
import com.squareup.picasso.Picasso

class TercerAdapter(val listener: ClickListener)
    : PagingDataAdapter<MovieData, TercerAdapter.ViewHolder>(DataDiff)
    , ClickListener {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    object DataDiff: DiffUtil.ItemCallback<MovieData>(){

        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.imageFound)
            Picasso.get().load("https://image.tmdb.org/t/p/w500${getItem(position)?.poster_path}")
            .into(image)


        image.setOnClickListener{
            getItem(position)?.let { it1 -> listener.OnItemClick(it1, image.context) }
        }
        //progressBar.visibility = View.GONE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false))
    }

    override fun OnItemClick(movie: MovieData, context: Context) {
        val intent = Intent(context, MovieDetails::class.java)
        intent.putExtra("movieId", movie.id.toString())
        intent.putExtra("posterImage", movie.poster_path)
        intent.putExtra("movieTitle", movie.title)
        intent.putExtra("releaseDate", movie.release_date)
        intent.putExtra("overView", movie.overview)
        intent.putExtra("voteAverage", movie.vote_average.toString())
        context.startActivity(intent)
    }

}