package com.example.tmdbmadproject.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdbmadproject.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val notificationsViewModel by viewModels<NotificationsViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

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