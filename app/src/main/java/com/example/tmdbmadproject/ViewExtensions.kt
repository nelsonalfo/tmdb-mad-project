package com.example.tmdbmadproject

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithNaveController(navController: NavController, topLevelDestinations: Set<Int>) {
    setupWithNavController(navController)

    navController.addOnDestinationChangedListener { _, destination, _ ->
        visibility = if (topLevelDestinations.contains(destination.id)) View.VISIBLE else View.GONE
    }
}