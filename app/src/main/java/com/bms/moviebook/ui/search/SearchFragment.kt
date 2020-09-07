package com.bms.moviebook.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bms.moviebook.R
import com.bms.moviebook.databinding.FragmentSearchBinding
import com.bms.moviebook.databinding.ItemSearchVideoBinding
import com.bms.moviebook.model.popular.MovieResponse
import com.bms.moviebook.util.Status
import com.bms.moviebook.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchMovieAdapter.OnRecyclerViewItemClick {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchMovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObserver()

    }

    private fun initViews() {
        binding.viewModel = viewModel
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        viewModel.videoResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    adapter = SearchMovieAdapter()
                    adapter.setOnnRecyclerViewItemClick(this)
                    adapter.setData(it.data!!)
                    binding.rvMovies.adapter = adapter
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    requireContext().toast(it.message.toString())
                }
            }
        })

        viewModel.searchString.observe(viewLifecycleOwner, Observer { searchString ->
            adapter.filter.filter(searchString)
        })
    }

    override fun onItemClick(
        model: MovieResponse.Result,
        binding: ItemSearchVideoBinding,
        position: Int
    ) {
        val extras = FragmentNavigatorExtras(
            binding.ivPoster to model.id.toString()
        )

        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
            id = model.id.toString(),
            movie = model
        )

        findNavController().navigate(
            action,
            extras
        )
    }
}