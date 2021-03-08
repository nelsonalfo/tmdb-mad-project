package com.example.tmdbmadproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdbmadproject.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel by viewModels<DashboardViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

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