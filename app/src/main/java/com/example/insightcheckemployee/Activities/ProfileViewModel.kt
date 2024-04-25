package com.example.insightcheckemployee.Activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _displayName = MutableLiveData<String>()
    val displayName: LiveData<String> get() = _displayName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    init {
        _displayName.value = auth.currentUser?.displayName ?: ""
        _email.value = auth.currentUser?.email ?: ""
    }
}
