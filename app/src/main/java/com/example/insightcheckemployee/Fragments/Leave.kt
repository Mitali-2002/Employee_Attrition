package com.example.insightcheckemployee.Fragments

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.insightcheckemployee.Constants.ARG_PARAM1
import com.example.insightcheckemployee.Constants.ARG_PARAM2
import com.example.insightcheckemployee.dataClass.LeaveRequest
import com.example.insightcheckemployee.databinding.FragmentLeaveBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Leave : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLeaveBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // Initialize Firebase
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLeaveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayofMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateLable(myCalendar)
        }

        binding.selectDayButton.setOnClickListener{
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH) , myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnSendRequest.setOnClickListener {
            val leaveMessage = binding.etLeave.text.toString()
            val startDate = binding.dayTextView.text.toString()

            if (leaveMessage.isNotEmpty() && startDate.isNotEmpty()) {
                val userId = auth.currentUser?.uid ?: ""
                val leaveRequest = LeaveRequest(leaveMessage, startDate)
                database.child("leaveRequests").child(userId).push().setValue(leaveRequest)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Leave request sent successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {exception ->
                        Log.e(TAG, "Failed to send leave request", exception)
                        Toast.makeText(requireContext(), "Failed to send leave request: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateLable(myCalendar: Calendar){
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.dayTextView.setText(sdf.format(myCalendar.time))
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Leave().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}