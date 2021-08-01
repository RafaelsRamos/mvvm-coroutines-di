package com.example.mvvmcoroutines.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmcoroutines.R
import com.example.mvvmcoroutines.models.Post
import dagger.hilt.android.qualifiers.ApplicationContext

class LivePostRecyclerAdapter(@ApplicationContext context: Context, posts: MutableList<Post>): LiveRecyclerViewAdapter<Post, LivePostRecyclerAdapter.ViewHolder>(context, posts) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), IBindableViewHolder<Post> {
        val title: TextView = itemView.findViewById(R.id.title)
        val body: TextView = itemView.findViewById(R.id.body)

        override fun bind(value: Post) {
            title.text = value.title
            body.text = value.body
        }
    }
}