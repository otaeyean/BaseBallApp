package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter(
    private val teams: List<Pair<String, Int>>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val (teamName, logoResId) = teams[position]
        holder.teamName.text = teamName
        holder.teamLogo.setImageResource(logoResId)

        holder.itemView.setOnClickListener {
            onItemClick(teamName)
        }
    }

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamLogo: ImageView = itemView.findViewById(R.id.team_logo)
        val teamName: TextView = itemView.findViewById(R.id.team_name)
    }
}