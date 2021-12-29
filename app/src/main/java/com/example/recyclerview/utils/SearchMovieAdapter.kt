package com.example.recyclerview.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.squareup.picasso.Picasso


//val movieList: ArrayList<MovieData>, val listener: ClickListener
class SearchMovieAdapter(val movieList: ArrayList<MovieData>, val listener: ClickListener) {


// RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder>(),

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.imageFound)
        fun bindInfo(movie: MovieData) {
            itemView.setOnClickListener {
                listener.OnItemClick(movie, itemImage.context)
            }


            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(itemImage)

        }

    }

}