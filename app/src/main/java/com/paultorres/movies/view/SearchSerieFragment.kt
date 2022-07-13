package com.paultorres.movies.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paultorres.movies.R
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.databinding.FragmentSearchSerieBinding
import com.paultorres.movies.util.SERIE_TYPE
import com.paultorres.movies.viewmodel.Adapter.MovieAdapter
import com.paultorres.movies.viewmodel.SearchSerieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class SearchSerieFragment : Fragment(R.layout.fragment_search_serie) {

    private val searchViewModel by viewModels<SearchSerieViewModel>()

    // private lateinit var searchAdapter: SearchSerieAdapter
    val searchAdapter by lazy { MovieAdapter({ onItemSelected(it) }) }
    private var _binding: FragmentSearchSerieBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        _binding = FragmentSearchSerieBinding.bind(view)

        recycled()

        setHasOptionsMenu(true)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottom)

        view.visibility = View.GONE

    }

    private fun recycled() {
        binding.rvMovie.apply {
            adapter = searchAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.search.asFlow().collectLatest {
                searchAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search_movie, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvMovie.scrollToPosition(0)
                    searchViewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }


    private fun onItemSelected(it: MoviesEntity) {
        val action = SearchSerieFragmentDirections.actionNavSearchserieToNavDetails(SERIE_TYPE, it.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottom)

        view.visibility = View.VISIBLE
        super.onDestroyView()
        _binding = null
    }
}