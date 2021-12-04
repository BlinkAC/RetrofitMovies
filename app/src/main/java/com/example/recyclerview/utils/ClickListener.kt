package com.example.recyclerview.utils

import android.content.Context

interface ClickListener {
    fun OnItemClick(movie: MovieData, context: Context)
}