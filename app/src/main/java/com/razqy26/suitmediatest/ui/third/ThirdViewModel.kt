package com.razqy26.suitmediatest.ui.third

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razqy26.suitmediatest.response.UsersResponse
import com.razqy26.suitmediatest.response.DataItem
import com.razqy26.suitmediatest.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThirdViewModel : ViewModel() {

    private var currentPage = 1
    private var totalPages = 2

    private val apiService = ApiConfig.getApiService()
    private val _userList = MutableLiveData<List<DataItem?>>()

    val userList: LiveData<List<DataItem?>>
        get() = _userList

    fun fetchUserList() {
        apiService.getUsers(1, 12)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                    if (response.isSuccessful) {
                        totalPages = response.headers()["total_pages"]?.toInt() ?: 1
                        val userList = response.body()!!.data
                        _userList.value = userList!!
                        if (currentPage < totalPages) {
                            fetchNextPage()
                        }
                    } else {
                        Log.e("ThirdViewModel", "API error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                }
            })
    }

    fun fetchNextPage() {
        if (currentPage <= totalPages) {
            currentPage++
            apiService.getUsers(currentPage, 12)
                .enqueue(object : Callback<UsersResponse> {
                    override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                        if (response.isSuccessful) {
                            fetchNextPage()
                        }
                    }

                    override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    }
                })
        }
    }
}
