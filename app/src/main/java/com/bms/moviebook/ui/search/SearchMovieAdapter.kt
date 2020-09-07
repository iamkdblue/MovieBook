package com.bms.moviebook.ui.search

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bms.moviebook.databinding.ItemSearchVideoBinding
import com.bms.moviebook.model.popular.MovieResponse


class SearchMovieAdapter() : RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>(), Filterable {

    var videosList: MutableList<MovieResponse.Result> = ArrayList()
    var videosFilteredList: MutableList<MovieResponse.Result> = ArrayList()
    private var titles = arrayOf<String>("Popular Movies", "Top Rated")
    private var onRecyclerViewItemClick: OnRecyclerViewItemClick? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMovieAdapter.ViewHolder = ViewHolder(
        ItemSearchVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return videosFilteredList.size
    }

    override fun onBindViewHolder(holder: SearchMovieAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(videosList: MutableList<MovieResponse.Result>) {
        this.videosList = videosList
        this.videosFilteredList = videosList
    }

    interface OnRecyclerViewItemClick {
        fun onItemClick(
            model: MovieResponse.Result,
            binding: ItemSearchVideoBinding,
            position: Int
        )
    }

    fun setOnnRecyclerViewItemClick(onnRecyclerViewItemClick: OnRecyclerViewItemClick) {
        onRecyclerViewItemClick = onnRecyclerViewItemClick
    }

    inner class ViewHolder(private val binding: ItemSearchVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(position: Int) {

            binding.item = videosList[position]
            binding.ivPoster.transitionName = videosList[position].id.toString()

            binding.btnBook.setOnClickListener {
                onRecyclerViewItemClick!!.onItemClick(videosList[position], binding, position)
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                var filteredList: MutableList<MovieResponse.Result>? = null
                if (charString.isEmpty()) {
                    videosFilteredList = videosList
                } else {
                    filteredList = java.util.ArrayList<MovieResponse.Result>()
                    for (row in videosList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.title.toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    videosFilteredList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = videosFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, filterResults: FilterResults) {
                videosFilteredList = filterResults.values as MutableList<MovieResponse.Result>
                notifyDataSetChanged()
            }
        }
    }
}