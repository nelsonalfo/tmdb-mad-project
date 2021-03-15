package com.example.tmdbmadproject.base

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

fun ImageView.loadFromUrl(fragment: Fragment, imageUrl: String, listener: ((loaded: Boolean) -> Unit)? = null) {
    if (imageUrl.isNotEmpty()) {
        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                listener?.invoke(false)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                listener?.invoke(true)
                return false
            }
        }

        Glide.with(fragment).load(imageUrl).listener(requestListener).into(this)
    }
}

fun ImageView.loadFromUrl(view: View, imageUrl: String, listener: ((loaded: Boolean) -> Unit)? = null) {
    if (imageUrl.isNotEmpty()) {
        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                listener?.invoke(false)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                listener?.invoke(true)
                return false
            }
        }

        Glide.with(view).load(imageUrl).listener(requestListener).into(this)
    }
}