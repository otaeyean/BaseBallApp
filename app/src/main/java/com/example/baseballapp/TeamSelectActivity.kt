package com.example.baseballapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseballapp.databinding.ActivityTeamSelectBinding

class TeamSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teams = listOf(
            "LG" to R.drawable.lg_logo,
            "KT" to R.drawable.kt_logo,
            "SSG" to R.drawable.ssg_logo,
            "NC" to R.drawable.nc_logo,
            "두산" to R.drawable.doosan_logo,
            "KIA" to R.drawable.kia_logo,
            "롯데" to R.drawable.lotte_logo,
            "삼성" to R.drawable.samsung_logo,
            "한화" to R.drawable.hanwha_logo,
            "키움" to R.drawable.kiwoom_logo
        )

        val adapter = TeamAdapter(teams) { teamName ->
            saveSelectedTeam(teamName)
            finish()
        }
        binding.teamRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.teamRecyclerView.adapter = adapter
    }

    private fun saveSelectedTeam(teamName: String) {
        val sharedPreferences = getSharedPreferences("baseballAppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selectedTeam", teamName)
        editor.apply()
    }
}