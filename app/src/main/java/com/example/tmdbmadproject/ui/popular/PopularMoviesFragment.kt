package com.example.tmdbmadproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbmadproject.base.BaseFragment
import com.example.tmdbmadproject.base.show
import com.example.tmdbmadproject.base.visible
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

        observeViewModel()

        loadPopularMovies()
    }

    private fun setupViews() = with(binding) {
        popularMoviesRetryButton.setOnClickListener { loadPopularMovies() }

        popularMoviesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
    }

    private fun loadPopularMovies() {
        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) { handleViewState(it) }
    }

    private fun handleViewState(viewState: PopularMoviesViewState) = with(binding) {
        popularMoviesErrorTextView.visible = viewState.isError
        popularMoviesRetryButton.visible = viewState.isError
        popularMoviesProgressBar.show = viewState.isLoading

        moviesAdapter.loadMovies(viewState.movies)
    }

    private fun onMovieSelected(movie: MovieResume) {
        val navFromHomeToDetail = PopularMoviesFragmentDirections.navFromHomeToDetail(movie.id)
        findNavController().navigate(navFromHomeToDetail)
    }
}