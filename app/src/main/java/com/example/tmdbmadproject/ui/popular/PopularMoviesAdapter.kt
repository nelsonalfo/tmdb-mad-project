package com.example.tmdbmadproject.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmadproject.base.loadFromUrl
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.databinding.ListItemMovieBinding

class PopularMoviesAdapter(private val listener: (movie: MovieResume, itemBinding: ListItemMovieBinding) -> Unit) : RecyclerView.Adapter<PopularMoviesViewHolder>() {
    private val dataSet: MutableList<MovieResume> = mutableListOf()

    fun loadMovies(movies: List<MovieResume>) {
        dataSet.clear()
        dataSet.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater, parent, false)

        return PopularMoviesViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(movie = dataSet[position])
    }

    override fun getItemCount(): Int {
        return if (dataSet.isEmpty()) 0 else dataSet.size
    }
}

class PopularMoviesViewHolder(
    private val binding: ListItemMovieBinding,
    private val listener: (movie: MovieResume, itemBinding: ListItemMovieBinding) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieResume) = with(binding) {
        root.setOnClickListener { listener.invoke(movie, binding) }
        movieTitle.text = movie.title
        movieDescription.text = movie.overview
        moviePosterImageView.apply {
            transitionName = movie.posterPath
            loadFromUrl(root, movie.posterUrl)
        }
    }
}