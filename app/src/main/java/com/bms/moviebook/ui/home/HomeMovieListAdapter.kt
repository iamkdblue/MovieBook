package com.bms.moviebook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bms.moviebook.databinding.ItemHomeVideoListBinding
import com.bms.moviebook.model.popular.MovieResponse

class HomeMovieListAdapter() : RecyclerView.Adapter<HomeMovieListAdapter.ViewHolder>() {

    var videosList: MutableList<MutableList<MovieResponse.Result>> = mutableListOf()
    private lateinit var homeMovieAdapter: HomeMovieAdapter
    private var titles = arrayOf<String>("Popular Movies", "Top Rated Movies")
    private var onRecyclerViewItemClick: HomeMovieAdapter.OnRecyclerViewItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMovieListAdapter.ViewHolder = ViewHolder(
        ItemHomeVideoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun onBindViewHolder(holder: HomeMovieListAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(videosList: MutableList<MutableList<MovieResponse.Result>>) {
        this.videosList = videosList
    }

    fun setOnnRecyclerViewItemClick(onnRecyclerViewItemClick: HomeMovieAdapter.OnRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onnRecyclerViewItemClick
    }


    inner class ViewHolder(private val binding: ItemHomeVideoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            homeMovieAdapter = HomeMovieAdapter()
            homeMovieAdapter.setData(videosList[position])
            homeMovieAdapter.setOnnRecyclerViewItemClick(onRecyclerViewItemClick)
            binding.tvHeading.text = titles[position]
            binding.rvItemList.adapter = homeMovieAdapter
        }
    }
}