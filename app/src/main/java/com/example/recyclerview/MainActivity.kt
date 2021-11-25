package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.MovieAdapter
import com.example.recyclerview.utils.MoviesResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val recyclerView:RecyclerView = findViewById(R.id.rvPrueba)
        getMyData()
        //val adapter = CustomAdapter()

        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //recyclerView.adapter = adapter

    }
    private fun getMyData(){

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(API_interface::class.java)

        val retrofitData = retrofitBuilder.getPopularMovies()
        retrofitData.enqueue(object : Callback<MoviesResponse?> {
            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if(response.isSuccessful){
                    rvPrueba.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MovieAdapter(response.body()!!.results)
                    }
                }
            }
            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}