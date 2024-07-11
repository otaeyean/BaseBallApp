package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GameListAdapter(val gameList:ArrayList<GameListData>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = LayoutInflater.from(parent?.context).inflate(R.layout.gamelistitem, parent, false)

        val team1:TextView=view.findViewById( R.id.team1)
        val team2:TextView=view.findViewById(R.id.team2)

        val game=gameList[position]
        team1.text=game.team1
        team2.text=game.team2

        return view
    }

    override fun getItem(position: Int): Any {
        return gameList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return gameList.size
    }

}