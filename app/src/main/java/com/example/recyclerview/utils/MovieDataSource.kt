package com.example.recyclerview.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recyclerview.services.API_service


class MovieDataSource(private val service: API_service, private val queryTitle: String): PagingSource<Int, MovieData>() {
    //var querySearch = "Christmas"
    private var TMDB_STARTING_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        try{
            val currentPage = params.key ?: TMDB_STARTING_INDEX
            val response = service.getMovies2(queryTitle, currentPage)
            val responseList = mutableListOf<MovieData>()
            val responseList2 = ArrayList<MovieData>()

            val data = response.body()?.results ?: emptyList()
            responseList.addAll(data)

            val prevKey = if(currentPage == 1)
                null else currentPage - 1

            Log.d("Cambios", "${responseList[responseList.size-1]}")

            if(currentPage>3){
                return  LoadResult.Page(
                    data = responseList2,
                    prevKey = null,
                    nextKey = null
                )
            }else{
                return  LoadResult.Page(
                    data = responseList,
                    prevKey,
                    currentPage.plus(1),
                )
            }



        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

}