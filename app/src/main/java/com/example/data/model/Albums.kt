package com.example.data.model


import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)