package com.example.baseballapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventsGrid: GridView
    private val apiService by lazy { ApiObject.getRetrofitService }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        eventsGrid = view.findViewById(R.id.events_grid)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = convertDateToServerFormat(year, month, dayOfMonth)
            fetchSchedule(selectedDate)
        }
        return view
    }

    private fun fetchSchedule(date: String) {
        apiService.getSchedule(date).enqueue(object : Callback<List<ScheduleData>> {
            override fun onResponse(call: Call<List<ScheduleData>>, response: Response<List<ScheduleData>>) {
                if (response.isSuccessful) {
                    val scheduleList = response.body() ?: emptyList()
                    val filteredList = scheduleList.filter { it.date == date }.take(5)
                    updateEventsGrid(filteredList)
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<List<ScheduleData>>, t: Throwable) {
                showErrorToast()
            }
        })
    }

    private fun updateEventsGrid(scheduleList: List<ScheduleData>) {
        val adapter = CustomScheduleAdapter(requireContext(), scheduleList)
        eventsGrid.adapter = adapter
    }

    private fun showErrorToast() {
        Toast.makeText(requireContext(), "Failed to fetch schedule.", Toast.LENGTH_SHORT).show()
    }

    private fun convertDateToServerFormat(year: Int, month: Int, day: Int): String {
        val date = Calendar.getInstance().apply {
            set(year, month, day)
        }.time
        val dateFormat = SimpleDateFormat("MM.dd(E)", Locale.KOREAN)
        return dateFormat.format(date)
    }
}