package com.example.tmdbmadproject.ui.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdbmadproject.databinding.FragmentTopratedMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {
    private var _binding: FragmentTopratedMoviesBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel by viewModels<TopRatedMoviesViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTopratedMoviesBinding.inflate(inflater, container, false)

        setupLiveDataObservers()

        return binding.root
    }

    private fun setupLiveDataObservers() {
        dashboardViewModel.text.observe(viewLifecycleOwner) { binding.textDashboard.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}