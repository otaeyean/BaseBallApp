package com.example.baseballapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.baseballapp.databinding.FragmentScheduleBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var schedule:TextView
    private lateinit var day:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDate()

        binding.calendar.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame, CalendarFragment())
                .commitAllowingStateLoss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDate() {
        schedule = binding.schedule
        day = binding.day

        var currentDate=LocalDate.now()

        fun updateDate(date: LocalDate) {
            //오늘 날짜
            val dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = date.format(dateFormat)

            //오늘 요일
            val dayOfWeek = date.dayOfWeek
            val dayOfWeekText = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN).substring(0, 1)

            schedule.text = formattedDate //날짜
            day.text = "($dayOfWeekText)" //요일
        }

        updateDate(currentDate)

        binding.left.setOnClickListener{ //전날로 이동
            currentDate=currentDate.minusDays(1)
            updateDate(currentDate)
        }

        binding.right.setOnClickListener { //다음날로 이동
            currentDate=currentDate.plusDays(1)
            updateDate(currentDate)
        }

    }



}