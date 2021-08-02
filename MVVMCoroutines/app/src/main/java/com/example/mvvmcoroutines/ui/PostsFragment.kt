package com.example.mvvmcoroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvmcoroutines.R
import com.example.mvvmcoroutines.databinding.FragmentPostsBinding
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.ui.activities.MainActivity
import com.example.mvvmcoroutines.ui.viewmodels.PostsFragmentViewModel
import com.example.mvvmcoroutines.utils.DataState
import com.example.mvvmcoroutines.utils.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class PostsFragment: Fragment() {


    private lateinit var binding: FragmentPostsBinding
    private val viewModel: PostsFragmentViewModel by viewModels()

    private val mainActivity get() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasInternetConnection()) {
            viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
                when (dataState) {
                    is DataState.Success<List<Post>> -> mainActivity.displayProgressBar(false)
                    is DataState.Error -> mainActivity.displayProgressBar(false)
                    is DataState.Loading -> mainActivity.displayProgressBar(true)
                }
            })
        }
    }


    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        viewModel.setStateEvent(PostsFragmentViewModel.StateEvent.GetCachedPostsEvents)
    }
}