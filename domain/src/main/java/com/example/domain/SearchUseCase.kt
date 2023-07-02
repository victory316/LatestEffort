package com.example.domain

interface SearchUseCase {

    fun searchImage(query: String, count: Int)

    fun searchVideo(query: String, count : Int)
}