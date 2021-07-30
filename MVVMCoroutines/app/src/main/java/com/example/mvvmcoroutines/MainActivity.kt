package com.example.mvvmcoroutines

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.ui.MainStateEvent
import com.example.mvvmcoroutines.ui.MainViewModel
import com.example.mvvmcoroutines.utils.DataState
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionListener {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isInternetAvailable()) {
            TedPermission.with(this)
                .setPermissionListener(this)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.INTERNET)
                .check()
        } else {

            onPermissionGranted()
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Post>> -> {
                    displayProgressBar(false)
                    appendPosts(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

    }

    private fun displayError(message: String?) {
        message?.let { text.text = it }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendPosts(posts: List<Post>) {
        val sb = StringBuilder()
        posts.forEach {
            sb.append("${it.title}\n")
        }
        text.text = sb.toString()
    }

    //// Handle Permissions

    override fun onPermissionGranted() {
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetPostEvents)
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }
}