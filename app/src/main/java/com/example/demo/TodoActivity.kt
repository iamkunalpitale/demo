package com.example.demo

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.demo.adapter.TodoAdapter
import com.example.demo.api.RetrofitService
import com.example.demo.databinding.ActivityTodoBinding
import com.example.demo.localdb.AppDatabase
import com.example.demo.model.MainViewModel
import com.example.demo.model.TodosModel
import com.example.demo.repository.MainRepository
import com.example.demo.util.NetworkUtil
import com.example.demo.view.ViewModelFactory

class TodoActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityTodoBinding
    private val adapter = TodoAdapter()
    var list = ArrayList<TodosModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        observeData()

        if (NetworkUtil.hasNetwork(application))
            viewModel.getAllTodos()
        else {
            viewModel.getAllTodosFromDB()
        }
    }

    private fun observeData() {

        viewModel.todoList.observe(this, {
            list.addAll(it)
            adapter.setTodos(list)
            if (it.size > 0)
                viewModel.addAllTodosInDB(it)
        })

        viewModel.todosFilterList.observe(this) {
            adapter.setTodos(it)
        }

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


    }

    private fun initData() {
        val retrofitService = RetrofitService.getInstance()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(mainRepository, db)
        ).get(MainViewModel::class.java)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)

        val item = menu!!.findItem(R.id.search_action)
        val searchView = item.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search Here"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var query = newText
                query = query.toString().trim()
                viewModel.serachTodosResult(query, list)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)

    }

}