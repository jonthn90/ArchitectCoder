package xyz.jonthn.architectcoder.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.model.MoviesRepository
import xyz.jonthn.architectcoder.ui.PermissionRequester
import xyz.jonthn.architectcoder.ui.common.getViewModel
import xyz.jonthn.architectcoder.ui.common.startActivity
import xyz.jonthn.architectcoder.ui.detail.DetailActivity
import xyz.jonthn.architectcoder.ui.main.MainViewModel.UiModel.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: MoviesAdapter

    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel { MainViewModel(MoviesRepository(application)) }

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        progress.visibility = if (model == Loading) View.VISIBLE else View.GONE

        when (model) {
            is Content -> adapter.movies = model.movies
            is Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie)
            }
            RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}
