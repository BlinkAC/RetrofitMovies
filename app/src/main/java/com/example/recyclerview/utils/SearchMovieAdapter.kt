package com.example.recyclerview.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.squareup.picasso.Picasso
import java.lang.reflect.Array
import kotlin.reflect.typeOf


class SearchMovieAdapter(val movieList: ArrayList<MovieData>, val listener: ClickListener) :
    RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: LayoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(view.inflate(R.layout.search_item, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        return holder.bindInfo(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }



    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.imageFound)
        fun bindInfo(movie: MovieData) {
            itemView.setOnClickListener{
                //listener.OnItemClick(movie.id.toString(), image.context)
                listener.OnItemClick(movie, itemImage.context)
            }
            // title.text = movie.title
            if (movie.poster_path != null) {

                Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                    .into(itemImage)
            }
        }

    }

}