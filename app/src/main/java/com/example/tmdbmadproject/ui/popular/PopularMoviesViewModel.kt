package com.example.tmdbmadproject.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "This is popular movies Fragment" }
    val text: LiveData<String> = _text
}