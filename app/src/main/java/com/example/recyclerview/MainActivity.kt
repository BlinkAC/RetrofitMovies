package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.AllCategories
import com.example.recyclerview.utils.MainRecyclerViewAdapter
import com.example.recyclerview.utils.MoviesResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/"

class MainActivity : AppCompatActivity() {
    private var mainRecyclerView: RecyclerView? = null
    private var mainRecyclerViewAdapter: MainRecyclerViewAdapter? = null
    var allCategory: MutableList<AllCategories> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                if (response.isSuccessful) {
                    allCategory.add(AllCategories("Todos hablan de ellas", response.body()!!.results))
                    allCategory.add(AllCategories("Prueba 1", response.body()!!.results))
                }
                setMainRecyclerView(allCategory)
            }
            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }


    private fun setMainRecyclerView(allCategory: List<AllCategories>) {
        mainRecyclerView = findViewById(R.id.parent_RecyclerView)
        mainRecyclerView?.apply {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
            mainRecyclerView!!.layoutManager = layoutManager
            adapter = MainRecyclerViewAdapter(allCategory)
        }
        Log.d("Esta es la lista", allCategory.toString())

    }
    /* val retrofitData = retrofitBuilder.getPopularMovies()
        retrofitData.enqueue(object : Callback<MoviesResponse?> {
            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if (response.isSuccessful) {
                    allCategory.add(AllCategories("Todos hablan de ellas", response.body()!!.results))
                    allCategory.add(AllCategories("Prueba 1", response.body()!!.results))
                    allCategory.add(AllCategories("Prueba 2", response.body()!!.results))
                    allCategory.add(AllCategories("Prueba 3", response.body()!!.results))
                }
                setMainRecyclerView(allCategory)
            }
            override fun onFailure(call: Call<MoviesResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })*/
}