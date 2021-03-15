package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.tmdbmadproject.R
import com.example.tmdbmadproject.base.BaseFragment
import com.example.tmdbmadproject.base.loadFromUrl
import com.example.tmdbmadproject.base.show
import com.example.tmdbmadproject.base.visible
import com.example.tmdbmadproject.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val detailViewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitions()
    }

    private fun setupTransitions() {
        enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        observeViewModel()

        loadMovieDetail()
    }

    private fun setupViews() {
        binding.movieDetailRetryButton.setOnClickListener { loadMovieDetail() }
    }

    private fun observeViewModel() {
        detailViewModel.viewState.observe(viewLifecycleOwner) { handleViewState(it) }
    }

    private fun loadMovieDetail() {
        detailViewModel.sendIntention(MovieDetailIntentions.LoadDetails(args.movieId))
    }

    private fun handleViewState(viewState: MovieDetailViewState) = with(binding) {
        progressBar.show = viewState.loading
        movieDetailErrorContainer.visible = viewState.error
        movieDetailContainer.visible = !viewState.error && !viewState.loading

        if (viewState.error) {
            startPostponedEnterTransition()
        } else {
            movieBackdropImageView.loadFromUrl(this@MovieDetailFragment, viewState.backdropUrl) {
                moviePosterImageView.loadFromUrl(this@MovieDetailFragment, viewState.posterUrl) {
                    movieGenders.text = viewState.genres
                    movieTitle.text = viewState.movieTitle
                    movieDescription.text = viewState.overView
                    movieVotes.text = getVotesText(viewState)
                    movieVotes.visible = movieVotes.text.isNotEmpty()

                    startPostponedEnterTransition()
                }
            }
        }
    }

    private fun getVotesText(viewState: MovieDetailViewState) = when {
        viewState.votes > 0 && viewState.ranking > 0 -> getString(R.string.ranking_and_votes, viewState.ranking, viewState.votes)
        viewState.votes > 0 -> getString(R.string.only_votes, viewState.votes)
        viewState.votes > 0 -> getString(R.string.only_ranking, viewState.ranking)
        else -> ""
    }
}

