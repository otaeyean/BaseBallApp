package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HitterRankAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var hitterList: List<HitterRankData> = emptyList()

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hitter_rank_header_item, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hitter_rank_item, parent, false)
            HitterRankViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HitterRankViewHolder && position > 0) {
            holder.bind(hitterList[position - 1])  // Position을 1 감소시켜야 리스트 인덱스와 맞춤
        }
    }

    override fun getItemCount(): Int = hitterList.size + 1  // Header를 위해 +1

    fun setList(newList: List<HitterRankData>) {
        hitterList = newList
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class HitterRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rank: TextView = itemView.findViewById(R.id.tv_rank)
        private val name: TextView = itemView.findViewById(R.id.tv_name)
        private val team: TextView = itemView.findViewById(R.id.tv_team)
        private val games: TextView = itemView.findViewById(R.id.tv_games)
        private val plateAppearance: TextView = itemView.findViewById(R.id.tv_plateAppearance)
        private val atBat: TextView = itemView.findViewById(R.id.tv_atBat)
        private val hits: TextView = itemView.findViewById(R.id.tv_hits)
        private val doubles: TextView = itemView.findViewById(R.id.tv_doubles)
        private val triples: TextView = itemView.findViewById(R.id.tv_triples)
        private val homeRuns: TextView = itemView.findViewById(R.id.tv_homeRuns)
        private val runBattedln: TextView = itemView.findViewById(R.id.tv_runBattedln)
        private val runScored: TextView = itemView.findViewById(R.id.tv_runScored)
        private val stolenBases: TextView = itemView.findViewById(R.id.tv_stolenBases)
        private val baseOnBall: TextView = itemView.findViewById(R.id.tv_baseOnBall)
        private val strikeOuts: TextView = itemView.findViewById(R.id.tv_strikeOuts)
        private val battingAVG: TextView = itemView.findViewById(R.id.tv_battingAVG)
        private val onBaseAVG: TextView = itemView.findViewById(R.id.tv_onBaseAVG)
        private val sluggingAVG: TextView = itemView.findViewById(R.id.tv_sluggingAVG)
        private val ops: TextView = itemView.findViewById(R.id.tv_ops)

        fun bind(hitterRankData: HitterRankData) {
            rank.text = hitterRankData.rank.toString()
            name.text = hitterRankData.name
            team.text = hitterRankData.team
            games.text = hitterRankData.games.toString()
            plateAppearance.text = hitterRankData.plateAppearance.toString()
            atBat.text = hitterRankData.atBat.toString()
            hits.text = hitterRankData.hits.toString()
            doubles.text = hitterRankData.doubles.toString()
            triples.text = hitterRankData.triples.toString()
            homeRuns.text = hitterRankData.homeRuns.toString()
            runBattedln.text = hitterRankData.runBattedIn.toString()
            runScored.text = hitterRankData.runsScored.toString()
            stolenBases.text = hitterRankData.stolenBases.toString()
            baseOnBall.text = hitterRankData.baseOnBall.toString()
            strikeOuts.text = hitterRankData.strikeOuts.toString()
            battingAVG.text = hitterRankData.battingAVG.toString()
            onBaseAVG.text = hitterRankData.onBaseAVG.toString()
            sluggingAVG.text = hitterRankData.sluggingAVG.toString()
            ops.text = hitterRankData.ops.toString()
        }
    }
}
