package com.example.baseballapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.GridView
import androidx.fragment.app.Fragment

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventsGrid: GridView
    private val events = hashMapOf<String, ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        eventsGrid = view.findViewById(R.id.events_grid)

        setupDummyEvents()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            updateEventsGrid(selectedDate)
        }
        return view
    }

    private fun setupDummyEvents() {
        val eventList1 = arrayListOf("안녕하세여", "제 캘린더 어떠신지", "이런식으로", "나타남니다", "아아")
        val eventList2 = arrayListOf("짠", "으으윽")
        val eventList3 = arrayListOf("와", "후")
        val eventList4 = arrayListOf("우", "오")

        events["2024-7-9"] = eventList1
        events["2024-7-10"] = eventList2
        events["2024-7-11"] = eventList3
        events["2024-7-12"] = eventList4
    }

    private fun updateEventsGrid(date: String) {
        val eventList = events[date] ?: arrayListOf()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, eventList)
        eventsGrid.adapter = adapter
    }
}