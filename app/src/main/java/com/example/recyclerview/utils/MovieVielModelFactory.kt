package com.example.recyclerview.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.services.API_service

class MovieVielModelFactory(private val service: API_service, private val queryTitle: String)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(service, queryTitle) as T
        }
        throw IllegalArgumentException("not found view model")
    }
}