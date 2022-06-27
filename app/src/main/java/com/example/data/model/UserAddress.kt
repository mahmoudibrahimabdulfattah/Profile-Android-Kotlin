package com.example.data.model


import com.google.gson.annotations.SerializedName

data class UserAddress(
    @SerializedName("street")
    val street: String? = null,
    @SerializedName("suite")
    val suite: String? = null,
    @SerializedName("city")
    val city: String? = null,
) {
    fun fullAddress(): String{
        return "$street, $suite, $city"
    }
}