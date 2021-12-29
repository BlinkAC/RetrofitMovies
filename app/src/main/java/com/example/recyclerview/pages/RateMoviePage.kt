package com.example.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.recyclerview.R
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.RateInfo
import com.example.recyclerview.utils.RateResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RateMoviePage : AppCompatActivity() {
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(com.example.recyclerview.BASE_URL)
        .build()
        .create(API_interface::class.java)

    private var rating = 0.5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie_page)


        val ratePoster: ImageView = findViewById(R.id.ratePoster)
        val rateTitle: TextView = findViewById(R.id.rateTitle)
        val rateDate: TextView = findViewById(R.id.rateDate)
        val rateAverage: TextView = findViewById(R.id.rateAverage)
        val rateOverView: TextView = findViewById(R.id.rateOverview)


        val bundle: Bundle? = intent.extras
        val movieID = bundle?.getString("movieId")
        val posterImage = bundle?.getString("posterImage")
        val movieTitle = bundle?.getString("movieTitle")
        val releaseDate = bundle?.getString("releaseDate")
        val overView = bundle?.getString("overView")
        val voteAverage = bundle?.getString("voteAverage")
        val sessionId = bundle?.getString("sessionID")

        val ratingCounter: TextView = findViewById(R.id.incrementalCounter)
        val plusButton: Button = findViewById(R.id.plusButton)
        val minusButton: Button = findViewById(R.id.minusButton)
        val submitRating: Button = findViewById(R.id.submitRate)

        Picasso.get().load("https://image.tmdb.org/t/p/original${posterImage}").into(ratePoster)
        rateTitle.text = movieTitle
        rateDate.text = releaseDate
        rateAverage.text = voteAverage
        rateOverView.text = overView
        ratingCounter.text = rating.toString()

        plusButton.setOnClickListener {
            if (rating<10){
                rating += 0.5
                ratingCounter.text = rating.toString()
            }
        }

        minusButton.setOnClickListener {
            if(rating>0.5){
                rating -= 0.5
                ratingCounter.text = rating.toString()
            }

        }

        submitRating.setOnClickListener {
            rateMovie(movieID.toString(), sessionId.toString(), rating)
        }


    }
    private fun rateMovie(movieId: String, sessionId: String, rateValue: Double){
        val rate = RateInfo(rateValue)
        val response = retrofitBuilder.rateMovie(movieId, sessionId, rate)
        response.enqueue(object: Callback<RateResponse?>{
            override fun onResponse(call: Call<RateResponse?>, response: Response<RateResponse?>) {
                //Log.d("Respuesta", response.toString())
                //Log.d("Respuesta2", response.body().toString())
                Toast.makeText(this@RateMoviePage, "¡Votación exitosa!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<RateResponse?>, t: Throwable) {
                Log.d("Mal",t.message.toString())
            }

        })
    }
}