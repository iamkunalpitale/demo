package com.example.demo.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.model.TodosModel
import com.example.demo.model.UserModel

@Dao
interface TodosDao {
    @Query("SELECT * FROM todosmodel")
    fun getAll(): List<TodosModel>

    @Insert
    fun insertAll(toDos: List<TodosModel>)

}