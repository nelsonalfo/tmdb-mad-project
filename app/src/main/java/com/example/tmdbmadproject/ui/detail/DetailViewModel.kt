package com.example.tmdbmadproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply { value = "Detail Screen" }
    val text: LiveData<String> = _text
}