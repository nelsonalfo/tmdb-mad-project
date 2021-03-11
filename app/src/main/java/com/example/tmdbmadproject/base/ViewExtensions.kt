package com.example.tmdbmadproject.base

import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithNaveController(navController: NavController, topLevelDestinations: Set<Int>) {
    setupWithNavController(navController)

    navController.addOnDestinationChangedListener { _, destination, _ ->
        visibility = if (topLevelDestinations.contains(destination.id)) VISIBLE else GONE
    }
}

var View.visible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

var ContentLoadingProgressBar.show: Boolean
    get() = visibility == VISIBLE
    set(value) {
        if (value) show() else hide()
    }

fun ImageView.loadFromUrl(fragment: Fragment, imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        Glide.with(fragment).load(imageUrl).into(this)
    }
}

fun ImageView.loadFromUrl(view: View, imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        Glide.with(view).load(imageUrl).into(this)
    }
}