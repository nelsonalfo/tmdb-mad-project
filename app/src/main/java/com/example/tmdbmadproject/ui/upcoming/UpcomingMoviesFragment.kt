package com.example.tmdbmadproject.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdbmadproject.databinding.FragmentUpcomingMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment() {
    private var _binding: FragmentUpcomingMoviesBinding? = null
    private val binding get() = _binding!!

    private val notificationsViewModel by viewModels<UpcomingMoviesViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)

        setupLiveDataObservers()

        return binding.root
    }

    private fun setupLiveDataObservers() {
        notificationsViewModel.text.observe(viewLifecycleOwner) { binding.textNotifications.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}