package com.example.tmdbmadproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor() : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "Detail Screen" }
    val text: LiveData<String> = _text
}