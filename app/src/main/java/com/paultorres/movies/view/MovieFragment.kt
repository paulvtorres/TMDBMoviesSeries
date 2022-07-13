package com.paultorres.movies.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paultorres.movies.R
import com.paultorres.movies.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.util.MOVIE_TYPE
import com.paultorres.movies.viewmodel.Adapter.MovieAdapter
import com.paultorres.movies.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalPagingApi
@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val moviesViewModel by viewModels<MovieViewModel>()
    val moviePopAdapter by lazy { MovieAdapter({ onItemSelected(it) }) }
    val movieTopAdapter by lazy { MovieAdapter({ onItemSelected(it) }) }
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieBinding.bind(view)
        Popular()
        Top()

        setHasOptionsMenu(true)
    }

    private fun Popular() {
        binding.rvMovieList1.apply {
            adapter = moviePopAdapter
            layoutManager = StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.HORIZONTAL
            )
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.pop.collectLatest {
                moviePopAdapter.submitData(it)
            }
        }

        moviePopAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    private fun Top() {
        binding.rvMovieList2.apply {
            adapter = movieTopAdapter
            layoutManager = StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.HORIZONTAL
            )
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            moviesViewModel.top.collectLatest {
                movieTopAdapter.submitData(it)
            }

        }

        movieTopAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val action = MovieFragmentDirections.actionNavMovieToNavSearchmovie()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onItemSelected(it: MoviesEntity) {
        val action = MovieFragmentDirections.actionNavMovieToNavDetails(MOVIE_TYPE, it.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}