package xyz.jonthn.architectcoder.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import xyz.jonthn.architectcoder.R
import xyz.jonthn.architectcoder.databinding.FragmentDetailBinding
import xyz.jonthn.architectcoder.ui.common.app
import xyz.jonthn.architectcoder.ui.common.bindingInflate
import xyz.jonthn.architectcoder.ui.common.getViewModel

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var component: DetailActivityComponent
    private val viewModel by lazy { getViewModel { component.detaiViewModel } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        component = app.component.plus(DetailActivityModule(args.id))

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }
    }
}
