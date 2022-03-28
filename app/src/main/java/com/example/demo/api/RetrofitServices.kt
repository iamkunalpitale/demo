package com.example.demo.api

import com.example.demo.model.TodosModel
import com.example.demo.model.UserModel
import com.example.demo.util.Constant.Companion.ACCESS_TOKEN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface RetrofitService {

    @GET("users")
    suspend fun gellAllUsers(): Response<List<UserModel>>

    @POST("users")
    suspend fun addUser(@Body userModel: UserModel): Response<UserModel>

    @GET("todos")
    suspend fun getAllTodos(): Response<List<TodosModel>>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES) // will wait actually establish a network connection to the server
                    .readTimeout(30, TimeUnit.SECONDS) // will wait to receive data once that connection has established
                    .writeTimeout(15, TimeUnit.SECONDS) // retrofit will wait between right attempts to the server once that connection has been established
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Authorization", "Bearer ${ACCESS_TOKEN}")
                        return@addNetworkInterceptor chain.proceed(builder.build())
                    }
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gorest.co.in/public/v2/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}