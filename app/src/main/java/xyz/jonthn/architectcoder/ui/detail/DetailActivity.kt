package xyz.jonthn.architectcoder.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.databinding.ActivityDetailBinding
import xyz.jonthn.architectcoder.model.server.MoviesRepository
import xyz.jonthn.architectcoder.ui.common.app
import xyz.jonthn.architectcoder.ui.common.getViewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel =
            getViewModel { DetailViewModel(intent.getIntExtra(MOVIE, -1), MoviesRepository(app)) }

        val binding: ActivityDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
}
