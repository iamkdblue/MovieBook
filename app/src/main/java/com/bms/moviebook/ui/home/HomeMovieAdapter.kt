package com.bms.moviebook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bms.moviebook.databinding.ItemHomeVideoBinding
import com.bms.moviebook.model.popular.PopularVideoResponse

class HomeMovieAdapter() : RecyclerView.Adapter<HomeMovieAdapter.ViewHolder>() {

    var videosList: List<PopularVideoResponse.Result> = ArrayList()
    private var titles = arrayOf<String>("Popular Movies","Top Rated")


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMovieAdapter.ViewHolder = ViewHolder(
        ItemHomeVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun onBindViewHolder(holder: HomeMovieAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(videosList: List<PopularVideoResponse.Result>) {
        this.videosList = videosList
    }


    inner class ViewHolder(private val binding: ItemHomeVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.item = videosList[position]
        }
    }
}