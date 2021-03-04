package com.example.tmdbmadproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdbmadproject.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)

        binding.toolbarLayout.toolbar.setupWithNavController(findNavController())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailViewModel.text.observe(viewLifecycleOwner, { value -> binding.detailText.text = value })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}