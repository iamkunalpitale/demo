package com.example.demo.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.TodoActivity
import com.example.demo.databinding.AdapterTodoBinding
import com.example.demo.databinding.AdapterUserBinding
import com.example.demo.model.UserModel
import com.example.demo.util.ValidationUtil


class UserDetailAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var userList = ArrayList<UserModel>()


    fun setUsers(users: List<UserModel>) {
        this.userList.clear()
        this.userList.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterUserBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val user = userList[position]
        if (ValidationUtil.validateUser(user)) {
            holder.binding.name.text = user.name
          //  holder.binding.email.text = user.email
            holder.binding.gender.text = user.gender
          // holder.binding.status.text = user.status

        }

        holder?.itemView.setOnClickListener {
            Log.v("he00", "veda")
            val intent = Intent(holder?.itemView.context, TodoActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }



//        if(user.status.equals("male",true)){
//            holder.binding.gender.setText("male");
//            holder.binding.gender.setTextColor(Color.parseColor("#567845"));
//        } else {
//            holder.binding.gender.setText("female");
//            holder.binding.gender.setTextColor(Color.parseColor("#567845"));
//
//        }

    }


    override fun getItemCount(): Int {
        return userList.size
    }
}

class MainViewHolder(val binding: AdapterUserBinding) : RecyclerView.ViewHolder(binding.root) {

}