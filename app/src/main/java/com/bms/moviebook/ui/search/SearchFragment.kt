package com.bms.moviebook.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bms.moviebook.R
import com.bms.moviebook.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.search_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}