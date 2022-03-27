package com.example.demo.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.localdb.AppDatabase
import com.example.demo.model.MainViewModel
import com.example.demo.repository.MainRepository

class ViewModelFactory constructor(private val repository: MainRepository,
                                   private val appDatabase: AppDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository,this.appDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}