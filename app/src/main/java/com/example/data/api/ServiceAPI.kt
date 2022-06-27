package com.example.data.api

import com.example.data.model.Albums
import com.example.data.model.Photos
import com.example.data.model.User
import com.example.data.model.UserAddress
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {

    @GET("users")
    suspend fun getUsersData(@Query("id") id: Int): MutableList<User>

    @GET("albums")
    suspend fun getAlbumsTitle(@Query("userId") userId: Int): MutableList<Albums>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): MutableList<Photos>
}