package com.example.mvvmcoroutines.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.ui.adapters.LivePostRecyclerAdapter
import com.example.mvvmcoroutines.ui.itemdecorators.SpacesItemDecoration
import com.example.mvvmcoroutines.ui.viewmodels.PostsFragmentViewModel
import com.example.mvvmcoroutines.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi


@BindingAdapter(value = ["setPostsViewModel", "setPostsLifecycleOwner"], requireAll = true)
fun fillPosts(rv: RecyclerView, viewModel: PostsFragmentViewModel, lifecycleOwner: LifecycleOwner) {
    rv.run {
        adapter = LivePostRecyclerAdapter(context, mutableListOf())
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(SpacesItemDecoration(18))
    }

    viewModel.dataState.observe(lifecycleOwner, Observer { dataState ->
        when (dataState) {
            is DataState.Success<List<Post>> -> {
                (rv.adapter as LivePostRecyclerAdapter).addItems(dataState.data)
            }
        }
    })
}

@ExperimentalCoroutinesApi
@BindingAdapter(value = ["updatePostsOnSwipe", "updatePostsLifecycleOwner"], requireAll = true)
fun updatePostsOnSwipe(srLayout: SwipeRefreshLayout, viewModel: PostsFragmentViewModel, lifecycleOwner: LifecycleOwner) {
    srLayout.setOnRefreshListener {
        viewModel.setStateEvent(PostsFragmentViewModel.StateEvent.GetPostsSlicedEvents)
    }

    viewModel.dataState.observe(lifecycleOwner, Observer { dataState ->
        srLayout.run {
            if (isRefreshing) {
                when (dataState) {
                    is DataState.Success<List<Post>> -> isRefreshing = false
                    is DataState.Error -> isRefreshing = false
                }
            }
        }
    })
}