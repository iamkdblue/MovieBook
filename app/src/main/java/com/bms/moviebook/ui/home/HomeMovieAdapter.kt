package com.bms.moviebook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bms.moviebook.R
import com.bms.moviebook.databinding.ItemHomeVideoBinding
import com.bms.moviebook.model.popular.MovieResponse


class HomeMovieAdapter() : RecyclerView.Adapter<HomeMovieAdapter.ViewHolder>() {

    var videosList: List<MovieResponse.Result> = ArrayList()
    private var titles = arrayOf<String>("Popular Movies", "Top Rated")


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

    fun setData(videosList: List<MovieResponse.Result>) {
        this.videosList = videosList
    }


    inner class ViewHolder(private val binding: ItemHomeVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var navController: NavController? = null

        fun bind(position: Int) {
            binding.item = videosList[position]

            binding.root.setOnClickListener { view ->

                val extras = FragmentNavigatorExtras(
                    binding.ivPoster to "ivPoster"
                )
                val bundle = bundleOf("movie" to videosList[position])
                view.findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle, null, extras)

            }
        }
    }
}