package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.pages.SearchPage
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.AllCategories
import com.example.recyclerview.utils.MainRecyclerViewAdapter
import com.example.recyclerview.utils.UserSession
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mytoolbar.view.*
import kotlinx.coroutines.*
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/"
val  SESSIONID: UserSession = UserSession()

class MainActivity : AppCompatActivity() {

    private var mainRecyclerView: RecyclerView? = null
    //private var mainRecyclerViewAdapter: MainRecyclerViewAdapter? = null
    private val seasonMovies = mutableListOf<CarouselItem>()
    private var allCategory: MutableList<AllCategories> = ArrayList()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(API_interface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myToolbar : Toolbar = findViewById(R.id.toolbar)
        val carousel: ImageCarousel = findViewById(R.id.carouselMovies)
        SESSIONID.MySession(this)

        myToolbar.searchMovieButton.setOnClickListener{
            val intent = Intent(this, SearchPage::class.java)
            startActivity(intent)
        }

        if(SESSIONID.getUserSession()!=""){
            myToolbar.menuDrawer.visibility = View.VISIBLE
        }else{
            myToolbar.menuDrawer.visibility = View.GONE
        }


        try{
            CoroutineScope(Dispatchers.Main).launch {
                 val seasonResponse =async {
                     retrofitBuilder.seasonMovies("193048|265857")
                 }.await()

                val popularResponse= async {
                    retrofitBuilder.getPopularMovies()
                }.await()

                val nowPlayingResponse = async {
                    retrofitBuilder.getNowPlaying()
                }.await()

                val upcomingResponse = async {
                    retrofitBuilder.getUpcomingMovies()
                }.await()

                //setMainRecyclerView(allCategory)
                if (seasonResponse.isSuccessful && nowPlayingResponse.isSuccessful
                    && upcomingResponse.isSuccessful && popularResponse.isSuccessful){

                    for(i in 0..4){
                        seasonMovies.add(CarouselItem("https://image.tmdb.org/t/p/w500${seasonResponse.body()!!.results[i].poster_path}"))
                    }

                    allCategory.add(AllCategories("Todos hablan de ellas", popularResponse.body()!!.results))
                    allCategory.add(AllCategories("Actualmente en cines", nowPlayingResponse.body()!!.results))
                    allCategory.add(AllCategories("Proximos lanzamientos", upcomingResponse.body()!!.results))

                    carousel.addData(seasonMovies)
                    setMainRecyclerView(allCategory)
                }else{
                    Log.d("AnError", "Went wrong")
                }
                Log.d("Listerine", allCategory[0].movieItem[0].title)
                Log.d("Listerine", allCategory[1].movieItem[0].title)
                Log.d("Listerine", allCategory[2].movieItem[0].title)
            }

        }catch (e: Exception){
            Log.d("Exception", e.localizedMessage.toString())
        }


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