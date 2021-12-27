package com.example.moviesapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.adapters.MovieRecyclerViewAdapter
import com.example.moviesapp.adapters.OnMovieClickListener
import com.example.moviesapp.databinding.FragmentFirstBinding
import com.example.moviesapp.models.Movie
import com.example.moviesapp.viewmodel.MovieViewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnMovieClickListener{

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRecyclerViewAdapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.getMovieMutableLiveData().observe(viewLifecycleOwner, object:Observer<Movie>{
            override fun onChanged(t: Movie?) {
                if(t != null){
                    movieRecyclerViewAdapter.updateData(mutableListOf(t))
                }
            }

        })
        movieRecyclerViewAdapter = activity?.let { MovieRecyclerViewAdapter(it, mutableListOf()) }!!

        movieRecyclerViewAdapter.setOnMovieClickListener(this)

        val movieRecyclerView: RecyclerView = view.findViewById(R.id.movieRecyclerView)

        movieRecyclerView.layoutManager = LinearLayoutManager(activity)
        movieRecyclerView.adapter = movieRecyclerViewAdapter
        movieRecyclerView.setHasFixedSize(true)

        val searchTitle: EditText = view.findViewById(R.id.searchTitle)

        searchTitle.setOnEditorActionListener{ v, action, event ->
            if(action == EditorInfo.IME_ACTION_DONE || action == EditorInfo.IME_ACTION_NEXT){
                val title: String = searchTitle.text.toString()
                movieViewModel.searchMoviesByTitle(title)

                true
            }
            else{
                Toast.makeText(activity,"Error!", Toast.LENGTH_LONG).show()
                false
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(imdbId: String) {
        val bundle = Bundle()
        bundle.putString("imdbId",imdbId)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
    }
}