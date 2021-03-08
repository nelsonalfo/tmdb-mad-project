package com.example.tmdbmadproject.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdbmadproject.R
import com.example.tmdbmadproject.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<PopularMoviesViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater)

        setupViews()
        setupLiveDataObservers()

        return binding.root
    }

    private fun setupViews() {
        binding.detailsButton.setOnClickListener { findNavController().navigate(R.id.nav_from_home_to_detail) }
    }

    private fun setupLiveDataObservers() {
        homeViewModel.text.observe(viewLifecycleOwner) { binding.textHome.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}