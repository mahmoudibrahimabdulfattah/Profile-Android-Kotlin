package com.example.ui.actiivty.second

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.RetrofitBuilder
import com.example.data.api.ServiceAPI
import com.example.data.model.Albums
import com.example.data.model.Photos
import com.example.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {
    private lateinit var context: Context
    private lateinit var apiService: ServiceAPI

    private lateinit var mutablePhotosList: MutableStateFlow<MutableList<Photos>>

    fun initViewModel(context: Context) {
        this.context = context

        apiService = RetrofitBuilder.apiservice

        mutablePhotosList = MutableStateFlow(ArrayList())

    }

    fun getPhotosList(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mutablePhotosList.emit(ArrayList())
            mutablePhotosList.emit(apiService.getPhotos(albumId))
        }
    }

    fun getPhotosList(): MutableStateFlow<MutableList<Photos>> {
        return mutablePhotosList
    }
}