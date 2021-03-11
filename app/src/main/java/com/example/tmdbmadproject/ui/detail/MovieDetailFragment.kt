package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.tmdbmadproject.R
import com.example.tmdbmadproject.base.BaseFragment
import com.example.tmdbmadproject.base.show
import com.example.tmdbmadproject.base.visible
import com.example.tmdbmadproject.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val detailViewModel by viewModels<MovieDetailViewModel>()


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.viewState.observe(viewLifecycleOwner) { handleViewState(it) }

        arguments?.let { bundle ->
            val args = MovieDetailFragmentArgs.fromBundle(bundle)
            detailViewModel.sendIntention(MovieDetailIntentions.LoadDetails(args.movieId))
        }
    }

    private fun handleViewState(viewState: MovieDetailViewState) = with(binding) {
        Log.d("NELSON", viewState.toString())

        progressBar.show = viewState.loading
        movieDetailContainer.visible = viewState.loading.not()

        movieGenders.text = viewState.genres
        movieTitle.text = viewState.movieTitle
        movieDescription.text = viewState.overView

        movieVotes.apply {
            text = when {
                viewState.votes > 0 && viewState.ranking > 0 -> getString(R.string.ranking_and_votes, viewState.ranking, viewState.votes)
                viewState.votes > 0 -> getString(R.string.only_votes, viewState.votes)
                viewState.votes > 0 -> getString(R.string.only_ranking, viewState.ranking)
                else -> ""
            }

            visible = text.isNotEmpty()
        }

        if (viewState.backdropUrl.isNotEmpty()) {
            Glide.with(this@MovieDetailFragment).load(viewState.backdropUrl).into(movieBackdropImage)
        }

        if (viewState.posterUrl.isNotEmpty()) {
            Glide.with(this@MovieDetailFragment).load(viewState.posterUrl).into(moviePoster)
        }
    }
}

