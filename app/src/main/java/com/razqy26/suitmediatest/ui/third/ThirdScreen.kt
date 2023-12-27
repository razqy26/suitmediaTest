package com.razqy26.suitmediatest.ui.third

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razqy26.suitmediatest.adapter.UserAdapter
import com.razqy26.suitmediatest.databinding.ActivityThirdScreenBinding
import com.razqy26.suitmediatest.response.DataItem
import com.razqy26.suitmediatest.ui.second.SecondScreen

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private val viewModel: ThirdViewModel by viewModels()
    private val adapter = UserAdapter { username ->
        val intent = Intent(this, SecondScreen::class.java)
        intent.putExtra("USERNAME", username)
        Log.e("tes",username)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchUserList()
        }

        binding.rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItems = adapter.itemCount
                if (lastVisibleItem >= totalItems - 1 && !binding.swipeRefreshLayout.isRefreshing) {
                    viewModel.fetchNextPage()
                }
            }
        })
        binding.rvUserList.adapter = adapter

        viewModel.fetchUserList()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userList.observe(this) { userList ->
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.submitList(userList)
            checkEmptyState(userList)
        }
    }

    private fun checkEmptyState(userList: List<DataItem?>) {
        if (userList.isEmpty()) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                @Suppress("DEPRECATION")
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}