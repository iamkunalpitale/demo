package com.example.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.demo.api.RetrofitService
import com.example.demo.databinding.ActivityCreateUserBinding
import com.example.demo.localdb.AppDatabase
import com.example.demo.model.MainViewModel
import com.example.demo.model.UserModel
import com.example.demo.repository.MainRepository
import com.example.demo.view.ViewModelFactory

class CreateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUserBinding
    lateinit var viewModel: MainViewModel
    lateinit var imgbtn: ImageButton


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgbtn = findViewById(R.id.backToMain)
        imgbtn.setOnClickListener {
            onBackPressed();

        }

        initData()
        observeData()
        onClick()


    }

    private fun onClick() {
        binding.buttonSave.setOnClickListener {

            val name = binding.editWord.text.toString().trim()
            val email = binding.editEmail.text.toString().trim()
            val gender = binding.spinner.selectedItem.toString()
            val status = binding.editStatus.text.toString().trim()
            val userModel =
                UserModel(id = null, name = name, email = email, gender = gender, status = status)

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(gender)) {
                Toast.makeText(this, "please enter gender", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(status)) {
                Toast.makeText(this, "please enter status", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addUser(userModel)
            }
        }
    }

    private fun initData() {
        val retrofitService = RetrofitService.getInstance()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        val mainRepository = MainRepository(retrofitService)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(mainRepository, db)
            ).get(MainViewModel::class.java)

    }

    private fun observeData() {

        viewModel.successMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }

        viewModel.errorMessage.observe(this)
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

