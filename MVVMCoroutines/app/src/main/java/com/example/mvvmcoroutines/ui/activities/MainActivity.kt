package com.example.mvvmcoroutines.ui.activities

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.mvvmcoroutines.R
import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.databinding.ActivityMainBinding
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

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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
                is DataState.Success<List<Post>> -> displayProgressBar(false)
                is DataState.Error -> displayProgressBar(false)
                is DataState.Loading -> displayProgressBar(true)
            }
        })

    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
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