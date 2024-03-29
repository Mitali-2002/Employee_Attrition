package com.example.insightcheckemployee.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.insightcheckemployee.Fragments.Home
import com.example.insightcheckemployee.R

class CompanyPolicies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_policies)


        val mainArrow: ImageView = findViewById(R.id.imageView9)
        mainArrow.setOnClickListener {
            // Replace Home fragment when the ImageView is clicked
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, Home())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}