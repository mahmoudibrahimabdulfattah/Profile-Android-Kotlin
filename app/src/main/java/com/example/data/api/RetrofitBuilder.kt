package com.example.data.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class RetrofitBuilder {
    companion object{
        private const val BaseURL: String = "https://jsonplaceholder.typicode.com/"

        fun getRetrofitBuilder(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiservice = getRetrofitBuilder().create(ServiceAPI::class.java)
    }
}