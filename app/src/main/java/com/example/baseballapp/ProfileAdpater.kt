package com.example.baseballapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.databinding.ItemMainBinding

class ProfileAdapter(private val context: Context, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    var datas = mutableListOf<ProfileData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ProfileViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileData) {
            binding.productName.text = item.name
            binding.price.text = item.price.toString()
            binding.productimage.setImageResource(item.img)

            binding.productName.setOnClickListener {
                when (item.name) {
                    "구단별 유니폼" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, UniformFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "구단별 모자" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, CapFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "[TEAMKOREA] 폼배트" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BatFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "[KBO]2024 KBO 공인구" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BallFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "[TEAMKOREA] KOREA 응원 머플러" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MufflerFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "[TEAM KOREA] PVC 글러브" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, GloveFragment())
                            .addToBackStack(null)
                            .commit()
                    }

                    else -> {

                    }
                }
            }
        }
    }
}