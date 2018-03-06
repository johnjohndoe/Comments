package com.example.api

import com.example.api.models.Comment
import retrofit2.Call
import retrofit2.http.GET

interface TypicodeService {

    @GET("comments")
    fun getComments(): Call<List<Comment>>

}
