package xyz.jonthn.architectcoder.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import xyz.jonthn.architectcoder.PermissionRequester
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.databinding.ActivityMainBinding
import xyz.jonthn.architectcoder.model.server.MoviesRepository
import xyz.jonthn.architectcoder.ui.common.EventObserver
import xyz.jonthn.architectcoder.ui.common.app
import xyz.jonthn.architectcoder.ui.common.getViewModel
import xyz.jonthn.architectcoder.ui.common.startActivity
import xyz.jonthn.architectcoder.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: MoviesAdapter

    private val coarsePermissionRequester =
        PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel {
            MainViewModel(MoviesRepository(app))
        }

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.navigateToMovie.observe(this, EventObserver { id ->
            startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, id)
            }
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })
    }
}
