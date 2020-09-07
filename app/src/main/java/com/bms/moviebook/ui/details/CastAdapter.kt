package com.bms.moviebook.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bms.moviebook.databinding.ItemCastBinding
import com.bms.moviebook.model.cast.CastResponse


class CastAdapter() : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    var castList: MutableList<CastResponse.Cast> = mutableListOf()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastAdapter.ViewHolder = ViewHolder(
        ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(videosList: MutableList<CastResponse.Cast>) {
        this.castList = videosList
    }


    inner class ViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.item = castList[position]
        }
    }
}