package com.example.recyclerview.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.pages.MovieDetails

class MainRecyclerViewAdapter
    (private val allCategory: List<AllCategories>) : ClickListener,
    RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    private var childRecyclerView: RecyclerView? = null
    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTitle: TextView
        var movieRV: RecyclerView

        init {
            categoryTitle = itemView.findViewById(R.id.parent_item_title)
            movieRV = itemView.findViewById((R.id.moviesRecyclerView))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.parent_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.categoryTitle.text = allCategory[position].categoryTitle
        setItemsMovieRecycler(holder.movieRV.context, holder.movieRV, allCategory[position].movieItem)
    }

    override fun getItemCount(): Int {
        return allCategory.size
    }

    private fun setItemsMovieRecycler(context: Context,recyclerView: RecyclerView, movieItem: List<MovieData>){
        recyclerView.apply {
            val layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context,RecyclerView.HORIZONTAL,false )
            recyclerView.layoutManager = layoutManager
            adapter = MovieAdapter(movieItem, this@MainRecyclerViewAdapter)
        }
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

        //Toast.makeText(context, "Titulo seleccionado: ${movieID}", Toast.LENGTH_LONG).show()
    }




}