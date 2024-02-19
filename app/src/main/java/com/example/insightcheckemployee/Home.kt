package com.example.insightcheckemployee


import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

import java.util.*

class Home : Fragment(), CalendarAdapter.onItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvDateMonth: TextView
    private lateinit var ivCalendarNext: ImageView
    private lateinit var ivCalendarPrevious: ImageView

    private val sdf = android.icu.text.SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var adapter: CalendarAdapter
    private val calendarList2 = ArrayList<CalendarDateModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        tvDateMonth = view.findViewById(R.id.text_date_month)
        recyclerView = view.findViewById(R.id.recyclerView)
        ivCalendarNext = view.findViewById(R.id.iv_calendar_next)
        ivCalendarPrevious = view.findViewById(R.id.iv_calendar_previous)
        val goToCompanyPoliciesButton: View = view.findViewById(R.id.companyPolicy)
        val goToSuggestionBox: View = view.findViewById(R.id.suggestionButton)
        setUpAdapter()

        ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }

        ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            setUpCalendar()
        }

        // Initial setup of calendar to display current date
        setUpCalendar()


        //        navigation to other pages
        goToCompanyPoliciesButton.setOnClickListener {
            val intent = Intent(activity, CompanyPolicies::class.java)
            startActivity(intent)
        }
        goToSuggestionBox.setOnClickListener {
            val intent1 = Intent(activity, SuggestionBox::class.java)
            startActivity(intent1)
        }

        return view
    }

    override fun onItemClick(text: String, date: String, day: String) {
        // Handle item click if needed
    }

    private fun setUpAdapter() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        adapter = CalendarAdapter { calendarDateModel: CalendarDateModel, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            adapter.setData(calendarList2)
            adapter.setOnItemClickListener(this)
        }
        recyclerView.adapter = adapter
    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)

        // Set calendar to the current date instead of the 1st day of the month
        monthCalendar.set(Calendar.DAY_OF_MONTH, currentDay)

        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)

            val isSelected = (
                    monthCalendar.get(Calendar.MONTH) == currentMonth &&
                            monthCalendar.get(Calendar.YEAR) == currentYear &&
                            monthCalendar.get(Calendar.DAY_OF_MONTH) == currentDay
                    )

            calendarList.add(CalendarDateModel(monthCalendar.time, isSelected))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        adapter.setOnItemClickListener(this)
        adapter.setData(calendarList)
    }










}
