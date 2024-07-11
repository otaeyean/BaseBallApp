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
                    "유니폼" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, UniformFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "하프 집업(네이비)" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, ZipupFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    "유니폼 키홀더" -> {
                        fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, KeyholderFragment())
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