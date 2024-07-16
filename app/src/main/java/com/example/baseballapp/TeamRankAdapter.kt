package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.databinding.TeamRankItemBinding
class TeamRankAdapter : RecyclerView.Adapter<TeamRankAdapter.TeamRankViewHolder>() {

    private var teamList: List<TeamRankData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamRankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_rank_item, parent, false)
        return TeamRankViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamRankViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    override fun getItemCount(): Int = teamList.size

    fun setList(newList: List<TeamRankData>) {
        teamList = newList
        notifyDataSetChanged()
    }

    inner class TeamRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rank: TextView = itemView.findViewById(R.id.tv_rank)
        private val teamName: TextView = itemView.findViewById(R.id.tv_teamName)
        private val games: TextView = itemView.findViewById(R.id.tv_games)
        private val wins: TextView = itemView.findViewById(R.id.tv_wins)
        private val draws: TextView = itemView.findViewById(R.id.tv_draws)
        private val losses: TextView = itemView.findViewById(R.id.tv_losses)
        private val winRate: TextView = itemView.findViewById(R.id.tv_winRate)
        private val winningMargin: TextView = itemView.findViewById(R.id.tv_winningMargin)
        private val continuity: TextView = itemView.findViewById(R.id.tv_continuity)

        fun bind(teamRankData: TeamRankData) {
            rank.text = teamRankData.rank.toString()
            teamName.text = teamRankData.teamName
            games.text = teamRankData.games.toString()
            wins.text = teamRankData.wins.toString()
            draws.text = teamRankData.draws.toString()
            losses.text = teamRankData.losses.toString()
            winRate.text = teamRankData.winRate.toString()
            winningMargin.text = teamRankData.winningMargin.toString()
            continuity.text = teamRankData.continuity
        }
    }
}

