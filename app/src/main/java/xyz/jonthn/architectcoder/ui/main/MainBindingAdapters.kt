package xyz.jonthn.architectcoder.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.jonthn.domain.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    (adapter as? MoviesAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}