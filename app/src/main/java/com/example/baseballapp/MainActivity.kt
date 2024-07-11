package com.example.baseballapp

import InformationFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.baseballapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(ScheduleFragment()) //시작 프래그먼트

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment= when(item.itemId){
                R.id.ScheduleFragment ->ScheduleFragment()
                R.id.RankingFragment -> RankingFragment()
                R.id.InformationFragment -> InformationFragment()
                R.id.CommunityFragment -> CommunityFragment()
                R.id.ShopFragment-> ShopFragment()
                else -> null
            }
            if(fragment!=null){
                setFragment(fragment)
            }
            true
        }

    }

    private fun setFragment(fragment:Fragment){
        val manager:FragmentManager=supportFragmentManager
        val fragTransaction=manager.beginTransaction()

        val currentFragment=manager.primaryNavigationFragment
        if(currentFragment!=null){
            fragTransaction.hide(currentFragment)
        }

        fragTransaction.replace(R.id.frame, fragment)
        fragTransaction.setPrimaryNavigationFragment(fragment)
        fragTransaction.commitAllowingStateLoss()
    }
}