package com.example.tmdbmadproject.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "This is upcoming movies Fragment" }
    val text: LiveData<String> = _text
}