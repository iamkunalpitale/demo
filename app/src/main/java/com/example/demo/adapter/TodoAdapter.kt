package com.example.demo.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.NewUserActivity
import com.example.demo.TodoActivity
import com.example.demo.databinding.AdapterTodoBinding
import com.example.demo.databinding.AdapterUserBinding
import com.example.demo.model.TodosModel
import com.example.demo.util.ValidationUtil


class TodoAdapter : RecyclerView.Adapter<MainViewTodoHolder>() {

    var todoList = ArrayList<TodosModel>()


    fun setTodos(todos: List<TodosModel>) {
        this.todoList.clear()
        this.todoList.addAll(todos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewTodoHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterTodoBinding.inflate(inflater, parent, false)
        return MainViewTodoHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewTodoHolder, position: Int) {

        val user = todoList[position]
        if (ValidationUtil.validateTodo(user)) {

            holder.binding.title.text = user.title
            holder.binding.status.text = user.status
//            holder.binding.dueOn.text = user.due_on



        }

//        holder?.itemView.setOnClickListener {
//            Log.v("he00", "veda")
//            val intent = Intent(holder?.itemView.context, NewUserActivity::class.java)
//            holder.itemView.context.startActivity(intent)
//        }


    }


    override fun getItemCount(): Int {
        return todoList.size
    }
}

class MainViewTodoHolder(val binding: AdapterTodoBinding) : RecyclerView.ViewHolder(binding.root) {

}