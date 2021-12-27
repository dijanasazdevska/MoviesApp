package com.example.moviesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.api.OmdbApi
import com.example.moviesapp.api.OmdbApiClient
import com.example.moviesapp.database.AppDatabase
import com.example.moviesapp.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(application: Application): AndroidViewModel(application) {

    private var omdbApi: OmdbApi
    private var movieMutableLiveData: MutableLiveData<Movie>
    private var app: Application
    private var appDatabase: AppDatabase = AppDatabase.getInstance(application)!!

    init{
        omdbApi = OmdbApiClient.getOmdbApi()!!
        movieMutableLiveData = MutableLiveData()
        app = application
    }
    fun searchMoviesByTitle(title: String){
       omdbApi.searchMoviesByTitle(title).enqueue(object: Callback<Movie>{
           override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
               if(response?.body() != null){
                   val movie:Movie = response?.body()
                   movieMutableLiveData.value = movie
                   CoroutineScope(Dispatchers.IO).launch {
                       appDatabase.movieDao().insertMovie(movie)
                   }
               }
               else{
                   Toast.makeText(app,"Error!",Toast.LENGTH_SHORT).show()
               }           }

           override fun onFailure(call: Call<Movie>?, t: Throwable?) {
              var m = t?.message
               Toast.makeText(app,m,Toast.LENGTH_LONG).show()           }
       })
    }

    fun getMovieMutableLiveData(): MutableLiveData<Movie> {
        return movieMutableLiveData
    }

}