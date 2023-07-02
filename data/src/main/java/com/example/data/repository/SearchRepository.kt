package com.example.data.repository

interface SearchRepository {

    fun searchImage(query: String, size: Int?)
}
