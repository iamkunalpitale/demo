package com.example.demo.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.localdb.AppDatabase
import com.example.demo.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModel constructor(
    private val mainRepository: MainRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val userList = MutableLiveData<List<UserModel>>()
    val todoList = MutableLiveData<List<TodosModel>>()
    val filterList = MutableLiveData<ArrayList<UserModel>>()
    val todosFilterList = MutableLiveData<ArrayList<TodosModel>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()



    // List of Users
    fun addUser(userModel: UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            try {
                val response = mainRepository.addUser(userModel)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        onSuceess(response.message())
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d("TAG", e.printStackTrace().toString())
            }

        }

    }

    // List of Users
    fun getAllUsers() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.gellAllUsers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    userList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    // Add List of Users in LocalDatabse
    fun addAllUsersInDB(list: List<UserModel>) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = appDatabase.userDao().insertAll(list)
                } catch (
                    e: Exception
                ) {
                    Log.d("TAG", e.printStackTrace().toString())
                }
            }
        }

    }

    // List of Users from LocalDatabse
    fun getAllUsersFromDB() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = appDatabase.userDao().getAll()
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    userList.postValue(response)
                    loading.value = false
                } else {
                    onError("No Data")
                }
            }
        }

    }

    // Add of Todos in LocalDatabse
    fun addAllTodosInDB(list: List<TodosModel>) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = appDatabase.todosDao().insertAll(list)
                } catch (
                    e: Exception
                ) {
                    Log.d("TAG", e.printStackTrace().toString())
                }
            }
        }

    }


    // List of Todos from LocalDatabse
    fun getAllTodosFromDB() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = appDatabase.todosDao().getAll()
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    todoList.postValue(response)
                    loading.value = false
                } else {
                    onError("No Data")
                }
            }
        }

    }

    // Todos
    fun getAllTodos() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllTodos()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    todoList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    private fun onSuceess(message: String) {
        successMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    // Search
    fun serachResult(query: String, list: ArrayList<UserModel>) {
        val filteredList1: ArrayList<UserModel> = ArrayList()
        for (i in 0 until list.size) {
            val text: String = list.get(i).name
            val gender:String = list.get(i).gender
            if (text.contains(query) || gender.contains(query)) {
                filteredList1.add(list.get(i))
            }
        }
        filterList.postValue(filteredList1)

    }

    fun serachTodosResult(query: String, list: ArrayList<TodosModel>) {
        val filteredList1: ArrayList<TodosModel> = ArrayList()
        for (i in 0 until list.size) {
            val text: String = list.get(i).title
            val status: String = list.get(i).status
            if (text.contains(query) || status.contains(query)) {
                filteredList1.add(list.get(i))
            }
        }
        todosFilterList.postValue(filteredList1)

    }



}