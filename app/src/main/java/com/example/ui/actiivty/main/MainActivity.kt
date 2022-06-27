package com.example.ui.actiivty.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Albums
import com.example.data.model.User
import com.example.ui.R
import com.example.ui.actiivty.second.SecondActivity
import com.example.ui.adapter.AlbumAdapter
import com.example.utils.UsersId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), AlbumAdapter.OnItemClick {
    lateinit var viewModel: MainViewModel
    private lateinit var context: Context

    private lateinit var user: User
    private lateinit var albums: MutableList<Albums>

    private lateinit var tv_username: TextView
    private lateinit var tv_address: TextView


    private lateinit var recyclerview: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter

    private var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        index = UsersId.randomUserId
        context = this
        tv_username = findViewById(R.id.tv_userName)
        tv_address = findViewById(R.id.tv_address)
        recyclerview = findViewById(R.id.recyclerview)

        user = User()
        albums = ArrayList()

        initList()
        initViewModel()
    }

    private fun initList() {
        albumAdapter = AlbumAdapter(mutableListOf(), this)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumAdapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.initViewModel(this)

        viewModel.getUserInfo(index)

        GlobalScope.launch {
            viewModel.getMutableUser().collect {
                user = it
                withContext(Dispatchers.Main)
                { setUIDate() }

                viewModel.getAlbumsList(user.id ?: index)
            }
        }

        GlobalScope.launch {
            viewModel.getAlbumsList().collect {
                albums.clear()
                albums.addAll(it)
                withContext(Dispatchers.Main) {
                    albumAdapter.refreshData(albums)
                }
            }
        }

    }

    private fun setUIDate() {
        tv_username.text = user.username
        tv_address.text = user.userAddress?.fullAddress().toString()
    }

    override fun onItemClicked(
        album: Albums,
        viewID: Int,
        position: Int,
        title: String,
        albumId: Int
    ) {
        Intent(context, SecondActivity::class.java).let {
            it.putExtra("title", title)
            it.putExtra("id", albumId)
            startActivity(it)
        }
    }
}