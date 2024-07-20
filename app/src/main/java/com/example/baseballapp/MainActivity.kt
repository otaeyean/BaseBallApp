package com.example.baseballapp
import RankingFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baseballapp.databinding.ActivityMainBinding
import com.example.yourapp.CommunityFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, ScheduleFragment())
            .commit()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.ScheduleFragment -> ScheduleFragment()
                R.id.RankingFragment -> RankingFragment()
                R.id.InformationFragment -> InformationFragment()
                R.id.CommunityFragment -> CommunityFragment()
                R.id.ShopFragment -> ShopFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, it)
                    .commit()
                true
            } ?: false
        }
    }
}