package com.example.insightcheckemployee.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.insightcheckemployee.ModelResponse.RegisterResponse
import com.example.insightcheckemployee.R
import com.example.insightcheckemployee.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register_Page : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginlink: TextView
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        // Hide action bar
        supportActionBar?.hide()

        name = findViewById(R.id.etSinUpName)
        email = findViewById(R.id.etSinUpEmail)
        password = findViewById(R.id.etSinUpPassword)
        register = findViewById(R.id.btnSignUp)
        loginlink = findViewById(R.id.tvLoginPage)

        loginlink.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignUp -> registerUser()
            R.id.tvLoginPage -> switchOnLogin()
        }
    }

    private fun registerUser() {
        val userName = name.text.toString()
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        // Your registration logic here
        if (userName.isEmpty()) {
            name.requestFocus()
            name.error = "Please enter your name"
        }

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

        val call: Call<RegisterResponse> = RetrofitClient
            .getInstance()
            .getApi()
            .register(userName, userEmail, userPassword)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val registerResponse: RegisterResponse? = response.body()

                if (response.isSuccessful) {
                    intent = Intent(this@Register_Page, SignIn_Page::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Toast.makeText(this@Register_Page, registerResponse?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Register_Page, registerResponse?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@Register_Page, t.message, Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun switchOnLogin() {
        // Your switch to login logic here
    }
}