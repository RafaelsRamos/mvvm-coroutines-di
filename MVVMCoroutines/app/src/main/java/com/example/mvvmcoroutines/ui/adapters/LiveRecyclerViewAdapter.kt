package com.example.mvvmcoroutines.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext

abstract class LiveRecyclerViewAdapter<T, VH>(@ApplicationContext context: Context, var data: MutableList<T>): RecyclerView.Adapter<VH>() where VH: RecyclerView.ViewHolder, VH: IBindableViewHolder<T> {
    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    fun updateItems(newItems: List<T>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }
}