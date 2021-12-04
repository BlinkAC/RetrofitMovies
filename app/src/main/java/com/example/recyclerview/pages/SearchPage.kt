package com.example.recyclerview.pages

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.*
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//val BASE_URL = "https://api.themoviedb.org/"

class SearchPage :
    ClickListener,
    AppCompatActivity() {
    lateinit var ed_text: EditText
    lateinit var listOfSearchedMovies: RecyclerView
    var searchedQuery: ArrayList<MovieData> = ArrayList()
    var indexPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        ed_text = findViewById(R.id.buscarPeli)
        ed_text.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                //Perform Code
                var query: String = ed_text.getText().toString()
                if(searchedQuery.isEmpty()){
                    setRecyclerSearch(query)
                }

                ed_text.getText().clear()
                hideKeyboard()
                Toast.makeText(this, "${query}", Toast.LENGTH_SHORT).show()
                return@OnKeyListener true
            }
            false
        })


    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun setRecyclerSearch(query: String) {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(com.example.recyclerview.BASE_URL)
            .build()
            .create(API_interface::class.java)
        val retrofitData = retrofitBuilder.searchMovies(query)
        retrofitData.enqueue(object : Callback<MoviesResponse?> {
            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if (response.isSuccessful) {
                    //searchedQuery.clear()
                    val perro = response.body()!!.results.size

                    for(i in 0 until perro)
                    searchedQuery.add(response.body()!!.results[i])

                }

                Log.d("Respuesta", "Lista respuesta ${searchedQuery.size} ")
                //index=index+1
                setMoviesRecyclerView(searchedQuery)
            }

            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                Toast.makeText(this@SearchPage, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setMoviesRecyclerView(searchedQuery: ArrayList<MovieData>) {
        listOfSearchedMovies = findViewById(R.id.searcheMovies)
        listOfSearchedMovies.apply {
            val layoutManager: RecyclerView.LayoutManager =
                GridLayoutManager(listOfSearchedMovies.context, 2)
            listOfSearchedMovies.layoutManager = layoutManager
            adapter = SearchMovieAdapter(searchedQuery, this@SearchPage)


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

        //Toast.makeText(context, "Titulo seleccionado: ${movie.title}", Toast.LENGTH_LONG).show()
    }


}