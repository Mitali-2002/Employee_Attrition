package com.example.insightcheckemployee.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.insightcheckemployee.Activities.LoginPage
import com.example.insightcheckemployee.Activities.ProfileViewModel
import com.example.insightcheckemployee.Constants.ARG_PARAM1
import com.example.insightcheckemployee.Constants.ARG_PARAM2
import com.example.insightcheckemployee.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.displayName.observe(viewLifecycleOwner) { displayName -> binding.nameTv.text = displayName
        }

//        viewModel.email.observe(viewLifecycleOwner) { email ->
//            binding.emailTv.text = email

        // Set the user's email
        val userEmail = auth.currentUser?.email
        binding.emailTv.text = userEmail










//        // Set the user's email
//        val userEmail = auth.currentUser?.email
//        binding.emailTv.text = userEmail

        binding.logoutTv.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                startActivity(Intent(requireContext(), LoginPage::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up resources
        _binding = null
    }
}