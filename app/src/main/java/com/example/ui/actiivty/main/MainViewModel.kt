package com.example.ui.actiivty.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.RetrofitBuilder
import com.example.data.api.ServiceAPI
import com.example.data.model.Albums
import com.example.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private lateinit var context: Context

    private lateinit var apiService: ServiceAPI

    private lateinit var mutableFlowUser: MutableStateFlow<User>
    private lateinit var mutableAlbumsList: MutableStateFlow<MutableList<Albums>>

    fun initViewModel(context: Context) {
        this.context = context

        apiService = RetrofitBuilder.apiservice

        mutableFlowUser = MutableStateFlow(User())
        mutableAlbumsList = MutableStateFlow(ArrayList())

    }

    fun getUserInfo(userID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableFlowUser.emit(apiService.getUsersData(userID)[0])
        }
    }

    fun getAlbumsList(userID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableAlbumsList.emit(ArrayList())
            mutableAlbumsList.emit(apiService.getAlbumsTitle(userID))
        }
    }

    //region getter
    fun getMutableUser(): MutableStateFlow<User> {
        return mutableFlowUser
    }

    fun getAlbumsList(): MutableStateFlow<MutableList<Albums>> {
        return mutableAlbumsList
    }
    //endregion

}