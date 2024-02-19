package com.example.insightcheckemployee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.insightcheckemployee.databinding.ActivityNavbarBinding

class NavBar : AppCompatActivity () {
    private lateinit var binding: ActivityNavbarBinding

    override fun onCreate(savedInstantState : Bundle ?){
        super.onCreate(savedInstantState)
        binding = ActivityNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
           when(it.itemId){
               R.id.home -> replaceFragment(Home())
               R.id.message -> replaceFragment(Message())
               R.id.upload -> replaceFragment(Upload())
               R.id.leave -> replaceFragment(Leave())
               R.id.profile -> replaceFragment(Profile())

               else -> {

               }

           }
            true

        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}