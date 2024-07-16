package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.databinding.PitcherRankItemBinding
class PitcherRankAdapter : RecyclerView.Adapter<PitcherRankAdapter.PitcherRankViewHolder>() {

    private var pitcherList: List<PitcherRankData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PitcherRankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pitcher_rank_item, parent, false)
        return PitcherRankViewHolder(view)
    }

    override fun onBindViewHolder(holder: PitcherRankViewHolder, position: Int) {
        holder.bind(pitcherList[position])
    }

    override fun getItemCount(): Int = pitcherList.size

    fun setList(newList: List<PitcherRankData>) {
        pitcherList = newList
        notifyDataSetChanged()
    }

    inner class PitcherRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rank: TextView = itemView.findViewById(R.id.tv_rank)
        private val name: TextView = itemView.findViewById(R.id.tv_name)
        private val team: TextView = itemView.findViewById(R.id.tv_team)
        private val games: TextView = itemView.findViewById(R.id.tv_games)
        private val wins: TextView = itemView.findViewById(R.id.tv_wins)
        private val losses: TextView = itemView.findViewById(R.id.tv_losses)
        private val save: TextView = itemView.findViewById(R.id.tv_save)
        private val hold: TextView = itemView.findViewById(R.id.tv_hold)
        private val innings: TextView = itemView.findViewById(R.id.tv_innings)
        private val pitchCount: TextView = itemView.findViewById(R.id.tv_pitchCount)
        private val hits: TextView = itemView.findViewById(R.id.tv_hits)
        private val homeRuns: TextView = itemView.findViewById(R.id.tv_homeRuns)
        private val strikeout: TextView = itemView.findViewById(R.id.tv_strikeout)
        private val baseOnBall: TextView = itemView.findViewById(R.id.tv_baseOnBall)
        private val runs: TextView = itemView.findViewById(R.id.tv_runs)
        private val earnedRuns: TextView = itemView.findViewById(R.id.tv_earnedRuns)
        private val earnedRunsAVG: TextView = itemView.findViewById(R.id.tv_earnedRunsAVG)
        private val whip: TextView = itemView.findViewById(R.id.tv_whip)
        private val qs: TextView = itemView.findViewById(R.id.tv_qs)

        fun bind(pitcherRankData: PitcherRankData) {
            rank.text = pitcherRankData.rank.toString()
            name.text = pitcherRankData.name
            team.text = pitcherRankData.team
            games.text = pitcherRankData.games.toString()
            wins.text = pitcherRankData.wins.toString()
            losses.text = pitcherRankData.losses.toString()
            save.text = pitcherRankData.save.toString()
            hold.text = pitcherRankData.hold.toString()
            innings.text = pitcherRankData.innings.toString()
            pitchCount.text = pitcherRankData.pitchCount.toString()
            hits.text = pitcherRankData.hits.toString()
            homeRuns.text = pitcherRankData.homeRuns.toString()
            strikeout.text = pitcherRankData.strikeout.toString()
            baseOnBall.text = pitcherRankData.baseOnBall.toString()
            runs.text = pitcherRankData.runs.toString()
            earnedRuns.text = pitcherRankData.earnedRuns.toString()
            earnedRunsAVG.text = pitcherRankData.earnedRunsAVG.toString()
            whip.text = pitcherRankData.whip.toString()
            qs.text = pitcherRankData.qs.toString()
        }
    }
}
