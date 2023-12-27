package com.razqy26.suitmediatest.retrofit

import com.razqy26.suitmediatest.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): Call<UsersResponse>
}