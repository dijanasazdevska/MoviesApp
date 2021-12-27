package com.example.moviesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Movie(@PrimaryKey val imdbID:String, val Title: String, val Released: String, val Genre: String, val Director: String, val Plot: String, val Poster: String, val imdbRating: Float){
}