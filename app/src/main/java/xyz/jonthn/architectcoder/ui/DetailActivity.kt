package xyz.jonthn.architectcoder.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.getParcelableExtra<Movie>(MOVIE)?.run {

            movieDetailToolbar.title = title

            val background = backdropPath ?: posterPath
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$background")

            movieDetailSummary.text = overview

            movieDetailInfo.setMovie(this)
        }
    }
}
