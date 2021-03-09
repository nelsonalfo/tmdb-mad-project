package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.tmdbmadproject.base.BaseFragment
import com.example.tmdbmadproject.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val detailViewModel by viewModels<MovieDetailViewModel>()


    override fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupLiveDataObservers()
    }

    private fun setupViews() {
        //binding.toolbarLayout.toolbar.setupWithNavController(findNavController())
    }

    private fun setupLiveDataObservers() {
        detailViewModel.text.observe(viewLifecycleOwner) { value -> binding.detailText.text = value }
    }
}

