package com.example.demo.repository

import com.example.demo.api.RetrofitService
import com.example.demo.localdb.AppDatabase

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun gellAllUsers() = retrofitService.gellAllUsers()
    suspend fun getAllTodos() = retrofitService.getAllTodos()
}