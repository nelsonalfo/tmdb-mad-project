package com.example.tmdbmadproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbmadproject.R
import com.example.tmdbmadproject.base.BaseFragment
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding>() {
    private val viewModel by viewModels<PopularMoviesViewModel>()

    private val moviesAdapter = PopularMoviesAdapter(this::onMovieSelected)


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPopularMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        viewModel.viewState.observe(viewLifecycleOwner) { handleViewState(it) }

        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)
    }

    private fun setupViews() = with(binding) {
        popularMoviesRetryButton.setOnClickListener { viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies) }

        popularMoviesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
    }

    private fun handleViewState(viewState: PopularMoviesViewState) = with(binding) {
        popularMoviesErrorTextView.visibility = if (viewState.isError) View.VISIBLE else View.GONE
        popularMoviesRetryButton.visibility = if (viewState.isError) View.VISIBLE else View.GONE

        popularMoviesProgressBar.apply { if (viewState.isLoading) show() else hide() }

        moviesAdapter.loadMovies(viewState.movies)
    }

    private fun onMovieSelected(movie: MovieResume) {
        findNavController().navigate(R.id.nav_from_home_to_detail)
    }
}