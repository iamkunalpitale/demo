package com.example.demo.localdb

import androidx.room.*
import com.example.demo.model.TodosModel
import com.example.demo.model.UserModel

@Dao
interface UserDao {
    @Query("SELECT * FROM usermodel")
    fun getAll(): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserModel)
}