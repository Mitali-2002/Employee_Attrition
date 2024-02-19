package com.example.insightcheckemployee.ModelResponse

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: User,

    @SerializedName("jwt")
    val jwt: String
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String
)
