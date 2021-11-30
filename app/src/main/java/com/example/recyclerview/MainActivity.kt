package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/"

class MainActivity : AppCompatActivity() {
    private var mainRecyclerView: RecyclerView? = null
    private var mainRecyclerViewAdapter: MainRecyclerViewAdapter? = null
    val holidayMovies = mutableListOf<CarouselItem>()
    var allCategory: MutableList<AllCategories> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val myToolbar : Toolbar = findViewById(R.id.toolbar).
        //setSupportActionBar(myToolbar)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(API_interface::class.java)
        val carousel: ImageCarousel = findViewById(R.id.carouselMovies)
        val retrofitData = retrofitBuilder.getPopularMovies()
        retrofitData.enqueue(object : Callback<MoviesResponse?> {
            override fun onResponse(
                call: Call<MoviesResponse?>,
                response: Response<MoviesResponse?>
            ) {
                if (response.isSuccessful) {
                    allCategory.add(AllCategories("Todos hablan de ellas", response.body()!!.results))
                    allCategory.add(AllCategories("Prueba 1", response.body()!!.results))
                    for(i in 0..4){
                        holidayMovies.add(CarouselItem("https://image.tmdb.org/t/p/w500${response.body()!!.results[i].poster_path}"))
                    }
                    //holidayMovies.add(CarouselItem(response.body()!!.results[2].toString()))
                    //Log.d("Lista links", "Esta es la lista de links: ${response.body()!!.results.poster_path}")
                }

                carousel.addData(holidayMovies)
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
        //Log.d("Esta es la lista", allCategory.toString())

    }

}