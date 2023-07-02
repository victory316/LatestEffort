package com.example.network

interface NetworkDataSource {

    fun getImages(query: String, pages: Int)

    fun getVideos(query: String, pages: Int)
}