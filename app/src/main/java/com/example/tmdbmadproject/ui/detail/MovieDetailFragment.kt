package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdbmadproject.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<MovieDetailViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        setupViews()
        setupLiveDataObservers()

        return binding.root
    }

    private fun setupViews() {
        //binding.toolbarLayout.toolbar.setupWithNavController(findNavController())
    }

    private fun setupLiveDataObservers() {
        detailViewModel.text.observe(viewLifecycleOwner) { value -> binding.detailText.text = value }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}