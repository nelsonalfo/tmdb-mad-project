package com.example.tmdbmadproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbmadproject.R
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PopularMoviesViewModel>()

    private val moviesAdapter = PopularMoviesAdapter(this::onMovieSelected)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupLiveDataObservers()

        viewModel.sendIntention(ViewIntention.LoadPopularMovies)
    }


    private fun setupViews() = with(binding) {
        popularMoviesRetryButton.setOnClickListener { viewModel.sendIntention(ViewIntention.LoadPopularMovies) }
        popularMoviesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
    }

    private fun setupLiveDataObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) { handleViewState(it) }
    }

    private fun handleViewState(viewState: ViewState) = with(binding) {
        moviesAdapter.loadMovies(viewState.movies)

        popularMoviesProgressBar.apply { if (viewState.isLoading) show() else hide() }

        popularMoviesErrorTextView.visibility = if (viewState.isError) View.VISIBLE else View.GONE
        popularMoviesRetryButton.visibility = if (viewState.isError) View.VISIBLE else View.GONE
    }

    private fun onMovieSelected(movie: MovieResume) {
        findNavController().navigate(R.id.nav_from_home_to_detail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}