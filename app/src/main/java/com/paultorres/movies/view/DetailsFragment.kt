package com.paultorres.movies.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.paultorres.movies.R
import com.paultorres.movies.databinding.FragmentDetailsBinding
import com.paultorres.movies.viewmodel.DetailsViewModel
import com.paultorres.movies.util.POSTER_BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment() : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()
    private val detailsViewModel: DetailsViewModel by viewModels()

    //  private val detailsViewModel by viewModels<DetailsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        detailsViewModel.getMovie(args.tipo, args.id)


        detailsViewModel.moviesDetailsModel.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(this@DetailsFragment)

                    .load("$POSTER_BASE_URL${it.poster_path}")

                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMoviePoster)

                binding.movieTitle.text = it.title
                binding.movieSinopsis.text = it.overview
                binding.movieRuntime.text = it.original_language
                binding.movieReleaseDate.text = it.release_date
                binding.movieRating.text = it.popularity
            }

        }

    }


}