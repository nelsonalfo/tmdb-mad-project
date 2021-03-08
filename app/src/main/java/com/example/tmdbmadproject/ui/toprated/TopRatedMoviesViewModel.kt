package com.example.tmdbmadproject.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "This is top rated movies Fragment" }
    val text: LiveData<String> = _text
}