package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayerAdapter(private val playerList: List<PlayerData>) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerImage: ImageView = itemView.findViewById(R.id.player_img)
        val playerName: TextView = itemView.findViewById(R.id.player_name)
        val playerPosition: TextView = itemView.findViewById(R.id.player_pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playerList[position]
        holder.playerImage.setImageResource(player.player_img)
        holder.playerName.text = player.player_name
        holder.playerPosition.text = player.player_pos
    }

    override fun getItemCount(): Int {
        return playerList.size
    }
}

