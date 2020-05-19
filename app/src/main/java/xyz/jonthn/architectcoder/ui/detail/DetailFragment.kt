package xyz.jonthn.architectcoder.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.data.AndroidPermissionChecker
import xyz.jonthn.architectcoder.data.PlayServicesLocationDataSource
import xyz.jonthn.architectcoder.data.database.RoomDataSource
import xyz.jonthn.architectcoder.data.server.TheMovieDbDataSource
import xyz.jonthn.architectcoder.databinding.FragmentDetailBinding
import xyz.jonthn.architectcoder.ui.common.app
import xyz.jonthn.architectcoder.ui.common.bindingInflate
import xyz.jonthn.architectcoder.ui.common.getViewModel
import xyz.jonthn.data.repository.MoviesRepository
import xyz.jonthn.data.repository.RegionRepository
import xyz.jonthn.usescases.FindMovieById
import xyz.jonthn.usescases.ToggleMovieFavorite

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = getViewModel {

            val moviesRepository = MoviesRepository(
                RoomDataSource(app.db),
                TheMovieDbDataSource(),
                RegionRepository(
                    PlayServicesLocationDataSource(app),
                    AndroidPermissionChecker(app)
                ),
                app.getString(R.string.api_key)
            )


            DetailViewModel(
                args.id, FindMovieById(moviesRepository),
                ToggleMovieFavorite(moviesRepository)
            )
        }

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }
    }
}
