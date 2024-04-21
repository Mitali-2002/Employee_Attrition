package com.example.insightcheckemployee.dataClass

data class LeaveRequest(
    val name : String,
    val leaveMessage : String,
    val startDate: String,
    val endDate: String,
)
