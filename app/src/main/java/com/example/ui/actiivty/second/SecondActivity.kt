package com.example.ui.actiivty.second

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.RetrofitBuilder
import com.example.data.model.Photos
import com.example.ui.R
import com.example.ui.actiivty.main.MainViewModel
import com.example.ui.adapter.PhotosAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {
    lateinit var viewModel: SecondViewModel
    private lateinit var context: Context

    private lateinit var photos: MutableList<Photos>

    private var albumId: Int? = null

    private lateinit var tv_titleSecond: TextView
    private lateinit var searchView: SearchView
    private lateinit var recyclerview: RecyclerView
    private lateinit var photoAdapter: PhotosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        context = this

        tv_titleSecond = findViewById(R.id.tv_titleSecond)
        recyclerview = findViewById(R.id.rv_images)
        searchView = findViewById(R.id.searchView)

        tv_titleSecond.text = intent.getStringExtra("title")
        albumId = intent.getIntExtra("id", 0)

        photos = ArrayList()


        initList()
        searchImages()
        initViewModel()
    }

    private fun initList() {
        photoAdapter = PhotosAdapter(mutableListOf(), mutableListOf(), this)
        recyclerview.apply {
            adapter = photoAdapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SecondViewModel::class.java]
        viewModel.initViewModel(this)

        viewModel.getPhotosList(albumId!!)

        GlobalScope.launch {
            viewModel.getPhotosList().collect {
                photos.clear()
                photos.addAll(it)
                withContext(Dispatchers.Main) {
                    photoAdapter.refreshData(photos)
                }
            }
        }
    }

    private fun searchImages(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                photoAdapter.filter.filter(newText)
                return true
            }
        })
    }
}