package com.example.mvvmcoroutines.ui.activities

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mvvmcoroutines.R
import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.databinding.ActivityMainBinding
import com.example.mvvmcoroutines.ui.adapters.MainFragmentStateAdapter
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabsAdapter = MainFragmentStateAdapter(this)

        TedPermission.with(this)
            .setPermissionListener(this)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.INTERNET)
            .check()

        //TODO("Remove below. Just for testing purposes)
        //runBlocking {
        //    postDao.nukeTable()
        //}
    }

    fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onPermissionGranted() {
        // Do nothing
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        // Do nothing
    }
}