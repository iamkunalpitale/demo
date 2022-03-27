package com.example.demo


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.adapter.UserDetailAdapter
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.model.MainViewModel
import com.example.demo.model.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewUserActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val adapter = UserDetailAdapter()
    lateinit var binding: ActivityMainBinding
    var list = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_new_user)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@NewUserActivity, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }

}