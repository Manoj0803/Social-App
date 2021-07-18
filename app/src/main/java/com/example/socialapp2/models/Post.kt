package com.example.socialapp2.models

data class Post(
    val text : String = "",
    val createdBy : User = User(),
    val createdAt : Long = 0L,
    val likedBy : ArrayList<String> = ArrayList()
)