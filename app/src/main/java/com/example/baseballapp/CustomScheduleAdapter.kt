package com.example.baseballapp

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomScheduleAdapter(private val context: Context, private val scheduleList: List<ScheduleData>) : BaseAdapter() {

    override fun getCount(): Int {
        return scheduleList.size
    }

    override fun getItem(position: Int): Any {
        return scheduleList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)

        val schedule = scheduleList[position]
        val scheduleTextView = view.findViewById<TextView>(R.id.schedule_text)

        val place = schedule.place
        val time = schedule.time
        val team1 = schedule.team1
        val team2 = schedule.team2

        val scheduleText = "$place  $time ]  $team1  vs  $team2"
        scheduleTextView.text = scheduleText

        val spannableString = SpannableString(scheduleText)

        val team1Start = scheduleText.indexOf(team1)
        val team1End = team1Start + team1.length
        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(R.color.blue)),
            team1Start,
            team1End,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val team2Start = scheduleText.indexOf(team2)
        val team2End = team2Start + team2.length
        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(R.color.blue)),
            team2Start,
            team2End,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val timeStart = scheduleText.indexOf(time)
        val timeEnd = timeStart + time.length
        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(R.color.gray)),
            timeStart,
            timeEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        scheduleTextView.text = spannableString

        return view
    }
}