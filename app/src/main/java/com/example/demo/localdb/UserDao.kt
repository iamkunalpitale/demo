package com.example.demo.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.model.TodosModel
import com.example.demo.model.UserModel

@Dao
interface UserDao {
    @Query("SELECT * FROM usermodel")
    fun getAll(): List<UserModel>

    @Insert
    fun insertAll(users: List<UserModel>)
}