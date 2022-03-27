package com.example.demo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodosModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userid") val user_id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "dueon") val due_on: String,
    @ColumnInfo(name = "status") val status: String
)