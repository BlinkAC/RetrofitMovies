package com.example.recyclerview.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.recyclerview.services.API_service

class MovieViewModel(private val service: API_service, val queryTitle: String): ViewModel() {

    val movieList = Pager(PagingConfig(initialLoadSize = 20, pageSize = 10)){
        MovieDataSource(service, queryTitle)
    }.flow.cachedIn(viewModelScope)
}