package com.example.moviesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentSecondBinding
import com.example.moviesapp.models.Movie
import com.example.moviesapp.viewmodel.MovieDetailsViewModel
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviePoster: ImageView = view.findViewById(R.id.movieDetailsPoster)
        val movieTitle: TextView = view.findViewById(R.id.movieDetailsTitle)
        val moviePlot: TextView = view.findViewById(R.id.movieDetailsPlot)
        val movieDirector: TextView = view.findViewById(R.id.movieDetailsDirector)
        val movieGenre: TextView = view.findViewById(R.id.movieDetailsGenre)
        val movieReleased: TextView = view.findViewById(R.id.movieDetailsReleased)
        val movieRating: RatingBar = view.findViewById(R.id.movieDetailsRating)

        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        movieDetailsViewModel.getMovieDetailsLiveData().observe(viewLifecycleOwner, object:
            Observer<Movie> {
            override fun onChanged(t: Movie?) {
                if(t != null){
                    movieTitle.text = t.Title
                    movieDirector.text= t.Director
                    movieGenre.text = t.Genre
                    moviePlot.text = t.Plot
                    movieReleased.text = t.Released

                    movieRating.rating = t.imdbRating.div(2)

                    activity?.let {
                        Glide.with(it)
                            .load(t.Poster)
                            .into(moviePoster)
                    }

                }
            }
        })
        val bundle = arguments
        if(bundle != null){
            val imdbId: String = bundle.getString("imdbId","")
            movieDetailsViewModel.loadMovieDetails(imdbId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}