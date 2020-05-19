package xyz.jonthn.architectcoder.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import xyz.jonthn.architectcoder.PermissionRequester
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.data.AndroidPermissionChecker
import xyz.jonthn.architectcoder.data.PlayServicesLocationDataSource
import xyz.jonthn.architectcoder.databinding.FragmentMainBinding
import xyz.jonthn.architectcoder.data.database.RoomDataSource
import xyz.jonthn.architectcoder.data.server.TheMovieDbDataSource
import xyz.jonthn.architectcoder.ui.common.EventObserver
import xyz.jonthn.architectcoder.ui.common.app
import xyz.jonthn.architectcoder.ui.common.bindingInflate
import xyz.jonthn.architectcoder.ui.common.getViewModel
import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.data.repository.RegionRepository
import xyz.jonthn.usescases.GetPopularMovies

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private lateinit var navController: NavController
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel = getViewModel {
            MainViewModel(
                GetPopularMovies(
                    MoviesRepository(
                        RoomDataSource(app.db),
                        TheMovieDbDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        app.getString(R.string.api_key)
                    )
                )
            )
        }

        viewModel.navigateToMovie.observe(viewLifecycleOwner, EventObserver { id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }
}