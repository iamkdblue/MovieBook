package com.bms.moviebook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bms.moviebook.R
import com.bms.moviebook.databinding.HomeFragmentBinding
import com.bms.moviebook.util.Status
import com.bms.moviebook.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var homeMovieListAdapter: HomeMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObserver()

    }

    private fun initViews() {
        binding.materialCard.setBackgroundResource(R.drawable.home_card_rounded_corners)
    }

    private fun initListeners() {
        binding.ivSearch.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(view).navigate(action)
        }

        binding.etSearch.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun initObserver() {
        viewModel.videoResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    homeMovieListAdapter = HomeMovieListAdapter()
                    homeMovieListAdapter.setData(it.data!!)
                    binding.rvMovies.adapter = homeMovieListAdapter
                }
                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    requireContext().toast(it.message.toString())
                }
            }
        })
    }
}