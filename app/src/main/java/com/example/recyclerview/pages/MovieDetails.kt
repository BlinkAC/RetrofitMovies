package com.example.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.recyclerview.R
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.ProvidersResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/"

class MovieDetails() :
    AppCompatActivity() {
    val providerList: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)


        //Log.d("Lista", "Link de provider: ${providerList}")
        val posterMovie: ImageView = findViewById(R.id.movieImage)
        val titleMovie: TextView = findViewById(R.id.movieTitle)
        val releaseMovie: TextView = findViewById(R.id.releaseDate)
        val publicVoteAverage: TextView = findViewById(R.id.voteAverage)
        val overviewMovie: TextView = findViewById(R.id.movieOverview)


        val bundle: Bundle? = intent.extras
        val movieID = bundle?.getString("movieId")
        val posterImage = bundle?.getString("posterImage")
        val movieTitle = bundle?.getString("movieTitle")
        val releaseDate = bundle?.getString("releaseDate")
        val overView = bundle?.getString("overView")
        val voteAverage = bundle?.getString("voteAverage")

        Picasso.get().load("https://image.tmdb.org/t/p/original${posterImage}").into(posterMovie)
        //Picasso.get().load("https://image.tmdb.org/t/p/original${providerList!![0]}").into(provider1)

        titleMovie.text = movieTitle
        releaseMovie.text = releaseDate
        publicVoteAverage.text = voteAverage
        overviewMovie.text = overView


        getMovieProviders(movieID.toString())


    }

    private fun getMovieProviders(movieID: String) {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(com.example.recyclerview.BASE_URL)
            .build()
            .create(API_interface::class.java)

        val retrofitData = retrofitBuilder.getWatchProviders(movieID)
        retrofitData.enqueue(object : Callback<ProvidersResponse?> {
            override fun onResponse(
                call: Call<ProvidersResponse?>,
                response: Response<ProvidersResponse?>
            ) {
                val prueba = response.body()!!.results.MX?.flatrate
                if (prueba!!.isEmpty()) {
                    setMovieProviders(providerList)
                } else {
                    val perrito: String? = response.body()!!.results.MX?.flatrate?.get(0)?.logoPath

                    providerList.add("https://image.tmdb.org/t/p/original${perrito}")
                    setMovieProviders(providerList)
                }


                //Log.d("Prueba", "Tamanio conio 777: ${providerList.size}")
            }

            override fun onFailure(call: Call<ProvidersResponse?>, t: Throwable) {
                Toast.makeText(this@MovieDetails, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
        //Log.d("Prueba 2", "Tamanio conio 777: ${providerList.size}")

    }

    private fun setMovieProviders(providerList: MutableList<String>) {
        if (providerList.isEmpty()) {
            val noProviders: TextView = findViewById(R.id.noProviders)
            noProviders.visibility = View.VISIBLE
        } else {
            val provider1: ImageView = findViewById(R.id.provider1)
            Picasso.get().load(providerList[0]).into(provider1)
        }
    }

}


