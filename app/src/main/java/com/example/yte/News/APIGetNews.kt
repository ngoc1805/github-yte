package com.example.yte.News


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.yte.address
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeService = retrofit.create(APIGetNews::class.java)

interface APIGetNews{
    @GET("get/tintuc/all")
    suspend fun getNews(): List<NewsClass>

    @GET("tintuc/id")
    suspend fun getNewsById(@Query("id") newsId: Int): NewsClass
}

class newsViewModel: ViewModel(){
    var newsList by mutableStateOf<List<NewsClass>>(emptyList())
        private set
     fun fetchNews(){
         viewModelScope.launch {
             try {
                 val response = recipeService.getNews()
                 newsList = response
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }
     }
    suspend fun getNewsById(newsId: Int): NewsClass? {
        try {
            val response = recipeService.getNewsById(newsId)
            return response
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getNewsCount(): Int {
        return newsList.size
    }
}

