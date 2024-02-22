package com.example.insightcheckemployee.ModelResponse

import com.google.gson.annotations.SerializedName

class RegisterResponse (
    @SerializedName("message")
    var message: String,

    @SerializedName("error")
    var error: String
) {
    // @JvmField annotation to expose the properties directly
    @JvmField
    var messageValue: String = message

    @JvmField
    var errorValue: String = error
}