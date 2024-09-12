package com.example.baseballapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.databinding.PlayerBinding

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerView>() {
    private var playerList= listOf<PlayerData>()

    class PlayerView(binding: PlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        val team:TextView=binding.team
        val playerName: TextView = binding.name
        val playerNumber:TextView=binding.number
        val playerPosition: TextView = binding.position
        val playerBirthday:TextView=binding.birthday
        val playerWeight:TextView=binding.weight
        val playerHeight:TextView=binding.height
        val playerHandedInfo:TextView=binding.handedInfo
        val playerImage:ImageView=binding.playerImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerView {
        val view = PlayerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlayerView(view)
    }

    override fun onBindViewHolder(holder: PlayerView, position: Int) {
        val player = playerList[position]
        holder.playerName.text = player.name
        holder.playerPosition.text = player.position
        holder.playerHeight.text = player.height
        holder.playerBirthday.text = player.birthday
        holder.playerNumber.text = player.number.toString()
        holder.playerWeight.text = player.weight
        holder.playerHandedInfo.text = player.handedInfo
        holder.team.text = player.team

        when (player.team) {
            "LG" -> holder.playerImage.setImageResource(R.drawable.lg_logo)
            "KT" -> holder.playerImage.setImageResource(R.drawable.kt_logo)
            "SSG" -> holder.playerImage.setImageResource(R.drawable.ssg_logo)
            "NC" -> holder.playerImage.setImageResource(R.drawable.nc_logo)
            "두산" -> holder.playerImage.setImageResource(R.drawable.doosan_logo)
            "KIA" -> holder.playerImage.setImageResource(R.drawable.kia_logo)
            "롯데" -> holder.playerImage.setImageResource(R.drawable.lotte_logo)
            "삼성" -> holder.playerImage.setImageResource(R.drawable.samsung_logo)
            "한화" -> holder.playerImage.setImageResource(R.drawable.hanwha_logo)
            "키움" -> holder.playerImage.setImageResource(R.drawable.kiwoom_logo)
            else -> holder.playerImage.setImageResource(R.drawable.baseball)
        }
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    fun setList(list: List<PlayerData>) {
        playerList = list.toMutableList()
        notifyDataSetChanged()
    }
}

