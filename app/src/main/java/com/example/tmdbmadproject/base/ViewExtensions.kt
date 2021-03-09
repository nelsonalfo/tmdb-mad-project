package com.example.tmdbmadproject.base

import android.view.View
import android.view.View.*
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
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