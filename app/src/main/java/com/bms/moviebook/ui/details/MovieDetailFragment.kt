package com.bms.moviebook.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.bms.moviebook.R
import com.bms.moviebook.databinding.MovieDetailFragmentBinding
import com.bms.moviebook.model.popular.MovieResponse
import com.bms.moviebook.ui.home.HomeMovieAdapter
import com.bms.moviebook.util.Status
import com.bms.moviebook.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movie: MovieResponse.Result
    private lateinit var binding: MovieDetailFragmentBinding
    private lateinit var castAdapter: CastAdapter
    private lateinit var homeMovieAdapter: HomeMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.movie_detail_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable<MovieResponse.Result>("movie")!!
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObserver()
        initCallApis()
    }

    private fun initViews() {
        binding.materialCard.setBackgroundResource(R.drawable.home_card_rounded_corners)
        binding.item = movie
    }

    private fun initListeners() {
    }

    private fun initCallApis() {
        viewModel.getMovieCredit(movieId = movie.id)
        viewModel.getSimilarMovie(movieId = movie.id)
    }

    private fun initObserver() {
        viewModel.castResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    castAdapter = CastAdapter()
                    castAdapter.setData(it.data!!)
                    binding.rvCast.adapter = castAdapter
                }
                Status.LOADING -> {
                    requireContext().toast("LOADING")
                }
                Status.ERROR -> {
                    requireContext().toast(it.message.toString())
                }
            }
        })

        viewModel.similarMovieResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    homeMovieAdapter = HomeMovieAdapter()
                    homeMovieAdapter.setData(it.data!!)
                    binding.rvSimilarMovies.adapter = homeMovieAdapter
                }
                Status.LOADING -> {
                    requireContext().toast("LOADING")
                }
                Status.ERROR -> {
                    requireContext().toast(it.message.toString())
                }
            }
        })
    }

}