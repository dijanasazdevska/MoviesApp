package com.example.moviesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.database.AppDatabase
import com.example.moviesapp.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(application: Application): AndroidViewModel(application) {
    private var app: Application = application

    private val database: AppDatabase = AppDatabase.getInstance(app)!!

    private var movieDetailsLiveData: MutableLiveData<Movie> = MutableLiveData()

    fun loadMovieDetails(imdbId: String){
        CoroutineScope(Dispatchers.IO).launch {
            val movie = database.movieDao().findById(imdbId)
            withContext(Dispatchers.Main){
                movieDetailsLiveData.value = movie }

        }
    }

    fun getMovieDetailsLiveData(): MutableLiveData<Movie> {
        return movieDetailsLiveData
    }


}