package com.bms.moviebook.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(position: Int) {
            binding.item = videosList[position]
            binding.ivPoster.transitionName = videosList[position].id.toString()

            binding.root.setOnClickListener { view ->

                val extras = FragmentNavigatorExtras(
                    binding.ivPoster to videosList[position].id.toString()
                )
                val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(id=videosList[position].id.toString(),movie=videosList[position])

                view.findNavController().navigate(
                    action,
                    extras
                )

                /*if (navController!!.currentDestination?.id == R.id.homeFragment) {

                } else {
                    val bundle = bundleOf("movie" to videosList[position])
                    view.findNavController().navigate(
                        R.id.action_movieDetailFragment_self,
                        bundle,
                        null,
                        extras
                    )
                }*/

            }
        }
    }
}