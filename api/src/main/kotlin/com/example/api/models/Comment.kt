package com.example.api.models

import com.squareup.moshi.Json

/*
{
  "postId": 1,
  "id": 1,
  "name": "id labore ex et quam laborum",
  "email": "Eliseo@gardner.biz",
  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
}
*/
data class Comment(

        @Json(name = "postId")
        val postId: Int = -1,

        @Json(name = "id")
        val remoteId: Int = -1,

        @Json(name = "name")
        val authorName: String = "",

        @Json(name = "email")
        val emailAddress: String = "",

        @Json(name = "body")
        val message: String = ""

)
