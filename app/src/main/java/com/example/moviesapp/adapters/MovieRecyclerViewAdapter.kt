package com.example.moviesapp.adapters

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.models.Movie

class MovieRecyclerViewAdapter(val context: Context, var movies: MutableList<Movie>): RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    private lateinit var onMovieClickListener: OnMovieClickListener

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val movieTitle: TextView
        val movieDate: TextView
        val moviePoster: ImageView
        lateinit var onMovieClickListener: OnMovieClickListener

        init{
            movieTitle = view.findViewById(R.id.movieTitle)
            movieDate = view.findViewById(R.id.movieDate)
            moviePoster = view.findViewById(R.id.moviePoster)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false)
        view.setOnClickListener{
            onMovieClickListener.onMovieClick(movies[0].imdbID)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie: Movie = movies[position]

        holder.movieTitle.text = currentMovie.Title
        holder.movieDate.text = currentMovie.Released
        Glide.with(context)
            .load(currentMovie.Poster)
            .into(holder.moviePoster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setOnMovieClickListener(onMovieClickListener: OnMovieClickListener){
        this.onMovieClickListener = onMovieClickListener
    }

    fun updateData(movieList: MutableList<Movie>){
        movies = movieList
        notifyDataSetChanged()
    }
}
interface OnMovieClickListener {
    fun onMovieClick(imdbId: String)
}