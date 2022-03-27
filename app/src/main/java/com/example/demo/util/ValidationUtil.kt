package com.example.demo.util

import com.example.demo.model.TodosModel
import com.example.demo.model.UserModel

object ValidationUtil {

    fun validateUser(user: UserModel) : Boolean {
        if (user.name.isNotEmpty() && user.email.isNotEmpty() && user.gender.isNotEmpty() && user.status.isNotEmpty()) {
            return true
        }
        return false
    }
    fun validateTodo(user: TodosModel) : Boolean {
        if (user.title.isNotEmpty() && user.status.isNotEmpty() && user.due_on.isNotEmpty()) {
            return true
        }
        return false
    }
}