package com.example.mvvmcoroutines.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.ui.MainViewModel
import com.example.mvvmcoroutines.ui.adapters.LivePostRecyclerAdapter
import com.example.mvvmcoroutines.ui.itemdecorators.SpacesItemDecoration
import com.example.mvvmcoroutines.utils.DataState


@BindingAdapter(value = ["setPostsViewModel", "setPostsLifecycleOwner"], requireAll = true)
fun fillPosts(rv: RecyclerView, mainViewModel: MainViewModel, lifecycleOwner: LifecycleOwner) {
    rv.run {
        adapter = LivePostRecyclerAdapter(context, mutableListOf())
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(SpacesItemDecoration(18))
    }

    mainViewModel.dataState.observe(lifecycleOwner, Observer { dataState ->
        when (dataState) {
            is DataState.Success<List<Post>> -> {
                (rv.adapter as LivePostRecyclerAdapter).updateItems(dataState.data)
            }
        }
    })
}