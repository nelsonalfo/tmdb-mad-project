package com.example.tmdbmadproject.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdbmadproject.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        setupViews()
        setupLiveDataObservers()

        return binding.root
    }

    private fun setupViews() {
        binding.toolbarLayout.toolbar.setupWithNavController(findNavController())
    }

    private fun setupLiveDataObservers() {
        detailViewModel.text.observe(viewLifecycleOwner) { value -> binding.detailText.text = value }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}