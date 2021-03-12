package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()

        return super.onCreateView(inflater, container, savedInstanceState)
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
        progressBar.show = false
        movieDetailErrorContainer.visible = viewState.error
        movieDetailContainer.visible = true

        movieGenders.text = viewState.genres
        movieTitle.text = viewState.movieTitle
        movieDescription.text = viewState.overView

        movieBackdropImageView.loadFromUrl(this@MovieDetailFragment, viewState.backdropUrl)
        moviePosterImageView.loadFromUrl(this@MovieDetailFragment, viewState.posterUrl)

        movieVotes.apply {
            text = when {
                viewState.votes > 0 && viewState.ranking > 0 -> getString(R.string.ranking_and_votes, viewState.ranking, viewState.votes)
                viewState.votes > 0 -> getString(R.string.only_votes, viewState.votes)
                viewState.votes > 0 -> getString(R.string.only_ranking, viewState.ranking)
                else -> ""
            }

            visible = text.isNotEmpty()
        }

        startPostponedEnterTransitionAfterDataLoaded()
    }

    private fun startPostponedEnterTransitionAfterDataLoaded() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.doOnPreDraw { startPostponedEnterTransition() }
    }
}

