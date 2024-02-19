package com.example.insightcheckemployee

import com.example.insightcheckemployee.ModelResponse.LoginResponse
import com.example.insightcheckemployee.ModelResponse.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login") // Adjust the endpoint as per your Django API
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
//        @Field("jwt") jwt: String
    ): Call<LoginResponse>
}