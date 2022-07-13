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
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.paultorres.movies.R
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.databinding.FragmentSerieBinding
import com.paultorres.movies.util.SERIE_TYPE
import com.paultorres.movies.viewmodel.Adapter.SerieAdapter
import com.paultorres.movies.viewmodel.SerieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class SerieFragment : Fragment(R.layout.fragment_serie) {

    private val seriesViewModel by viewModels<SerieViewModel>()

    val serieTopAdapter by lazy { SerieAdapter({ onItemSelected(it) }) }
    private lateinit var seriePopAdapter: SerieAdapter
    private var _binding: FragmentSerieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSerieBinding.bind(view)

        Popular()
        Top()

        setHasOptionsMenu(true)
    }

    fun Popular() {
        seriePopAdapter = SerieAdapter({ onItemSelected(it) })
        binding.rvMovieList1.apply {
            adapter = seriePopAdapter
            layoutManager = StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.HORIZONTAL
            )
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            seriesViewModel.pop.collectLatest {
                seriePopAdapter.submitData(it)
            }


        }
        seriePopAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    fun Top() {
        binding.rvMovieList2.apply {
            adapter = serieTopAdapter
            layoutManager = StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.HORIZONTAL
            )
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            seriesViewModel.top.collectLatest {
                serieTopAdapter.submitData(it)
            }

        }
        serieTopAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //  super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // navigate to settings screen
                val action = SerieFragmentDirections.actionNavSerieToNavSearchserie()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun onItemSelected(it: MoviesEntity) {
        val action = SerieFragmentDirections.actionNavSerieToNavDetails(SERIE_TYPE, it.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}