package com.example.mvvmcoroutines.ui.bindingadapters

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.databinding.BindingAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@BindingAdapter(value = ["android:adapter"])
fun setAdapter(viewPager: ViewPager2, adapter: FragmentStateAdapter) {
    viewPager.adapter = adapter
}

@BindingAdapter(value = ["setMainTabsViewPager"])
fun setMainTabs(tabLayout: TabLayout, viewPager: ViewPager2) {
    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        tab.text = "OBJECT ${(position + 1)}"
    }.attach()
}

@BindingAdapter("animation")
fun animate(view: View, @AnimRes animationId: Int) {
    view.run {
        startAnimation(AnimationUtils.loadAnimation(context, animationId))
    }
}