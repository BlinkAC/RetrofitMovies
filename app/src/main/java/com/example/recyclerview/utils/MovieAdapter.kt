package com.example.recyclerview.utils


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.pages.MovieDetails
import com.squareup.picasso.Picasso


class MovieAdapter(val movieList: List<MovieData>, val listener: ClickListener) : RecyclerView.Adapter<MovieAdapter.MoviesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view: LayoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(view.inflate(R.layout.card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bindInfo(movieList[position])
    }

    override fun getItemCount(): Int {
        return 10
    }


    inner class MoviesViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        //var title: TextView = itemView.findViewById(R.id.title)
        var image: ImageView = itemView.findViewById(R.id.image)
        fun bindInfo(movie: MovieData){
           // title.text = movie.title
            itemView.setOnClickListener{
                //listener.OnItemClick(movie.id.toString(), image.context)
                 listener.OnItemClick(movie, image.context)
            }
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(image)


        }
    }
}