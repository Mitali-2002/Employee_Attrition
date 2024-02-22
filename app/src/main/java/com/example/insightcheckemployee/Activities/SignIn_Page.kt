package com.example.insightcheckemployee.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.insightcheckemployee.ModelResponse.LoginResponse
import com.example.insightcheckemployee.R
import com.example.insightcheckemployee.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn_Page : AppCompatActivity(), View.OnClickListener{

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        email = findViewById(R.id.etSinInEmail)
        password = findViewById(R.id.etSinInPassword)
        loginButton = findViewById(R.id.btnSignIn)

        // Set click listener
        loginButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> loginUser()
        }
    }

    private fun loginUser() {
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        // Your registration logic here

        if (userEmail.isEmpty()) {
            email.requestFocus()
            email.error = "Please enter your email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.requestFocus()
            email.error = "Please enter a correct email"
            return
        }

        if (userPassword.isEmpty()) {
            password.requestFocus()
            password.error = "Please enter your password"
            return
        }

        if (userPassword.length < 8) {
            password.requestFocus()
            password.error = "Please enter a password with at least 8 characters"
            return
        }

        val call: Call<LoginResponse> = RetrofitClient
            .getInstance()
            .getApi()
            .login(userEmail, userPassword)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse: LoginResponse? = response.body()
                val token = loginResponse?.jwt
                if (response.isSuccessful) {

                    // Handle the received token, for example, save it to SharedPreferences
                    intent = Intent(this@SignIn_Page, NavBar::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Toast.makeText(this@SignIn_Page, "Login Successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle login failure
                    Toast.makeText(this@SignIn_Page, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure, e.g., network issues
                Log.e("API Call Failure", "Error: ${t.message}", t)
                Toast.makeText(this@SignIn_Page, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}