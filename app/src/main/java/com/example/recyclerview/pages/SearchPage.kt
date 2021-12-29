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
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.services.API_service
import com.example.recyclerview.utils.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchPage :
    ClickListener,
    AppCompatActivity() {
    private lateinit var editableText: EditText
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: TercerAdapter

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)
        editableText = findViewById(R.id.buscarPeli)


        editableText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                //Perform Code
                val queryTitle: String = editableText.text.toString()
                viewModel = ViewModelProvider(
                    this,
                    MovieVielModelFactory(API_service.getApi(), queryTitle)
                )[MovieViewModel::class.java]

                movieAdapter = TercerAdapter(this)


                lifecycleScope.launch {
                    viewModel.movieList.collectLatest {
                        movieAdapter.submitData(it)
                    }
                }


                bindUi()


                editableText.text.clear()
                hideKeyboard()

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


    private fun bindUi() {
        val rv = findViewById<RecyclerView>(R.id.searcheMovies)
        rv.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
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

    }


}