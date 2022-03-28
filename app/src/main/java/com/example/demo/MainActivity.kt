package com.example.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.demo.adapter.UserDetailAdapter
import com.example.demo.api.RetrofitService
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.localdb.AppDatabase
import com.example.demo.model.MainViewModel
import com.example.demo.model.UserModel
import com.example.demo.repository.MainRepository
import com.example.demo.view.ViewModelFactory
import com.example.demo.util.NetworkUtil.Companion.hasNetwork


class MainActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var viewModel: MainViewModel
    private val adapter = UserDetailAdapter()
    private lateinit var binding: ActivityMainBinding
    var list = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchView = findViewById(R.id.searchView)

        initData()
        observeData()
        onClick()

        if (hasNetwork(application))
            viewModel.getAllUsers()
        else {
            viewModel.getAllUsersFromDB()
        }
    }

    private fun onClick() {

        binding.fabAdd.setOnClickListener {
            openCreateUserActivityForResult()
        }

    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                list.clear()
                viewModel.getAllUsers()
            }
        }

    fun openCreateUserActivityForResult() {
        val intent = Intent(this, CreateUserActivity::class.java)
        resultLauncher.launch(intent)
    }


    private fun initData() {
        val retrofitService = RetrofitService.getInstance()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()


        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(mainRepository, db)
            ).get(MainViewModel::class.java)

    }

    private fun observeData() {
        viewModel.userList.observe(this) {
            list.addAll(it)
            adapter.setUsers(list)
            if (list.size > 0)
                viewModel.addAllUsersInDB(it)
        }

        viewModel.filterList.observe(this) {
            adapter.setUsers(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    var query = newText
                    query = query.toString().trim()
                    viewModel.serachResult(query, list)
                    return true
                }
            })

    }


}