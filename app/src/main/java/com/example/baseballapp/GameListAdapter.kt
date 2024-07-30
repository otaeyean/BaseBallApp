package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class GameListAdapter(val gameList:ArrayList<GameListData>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View = LayoutInflater.from(parent?.context).inflate(R.layout.gamelistitem, parent, false)

        val time:TextView=view.findViewById(R.id.time)
        val team1:TextView=view.findViewById(R.id.team1)
        val team2:TextView=view.findViewById(R.id.team2)
        val team1Score:TextView=view.findViewById(R.id.team1Score)
        val team2Score:TextView=view.findViewById(R.id.team2Score)
        val place:TextView=view.findViewById(R.id.place)
        val note:TextView=view.findViewById(R.id.note)
        val team1Image:ImageView=view.findViewById(R.id.team1image)
        val team2Image:ImageView=view.findViewById(R.id.team2image)

        val game=gameList[position]

        time.text=game.time
        team1.text=game.team1
        team2.text=game.team2
        team1Score.text=game.team1Score
        team2Score.text=game.team2Score
        place.text=game.place
        note.text=game.note

        when(game.team1){
            "LG" ->team1Image.setImageResource(R.drawable.lg_logo)
            "KT" -> team1Image.setImageResource(R.drawable.kt_logo)
            "SSG" ->team1Image.setImageResource(R.drawable.ssg_logo)
            "NC" -> team1Image.setImageResource(R.drawable.nc_logo)
            "두산" -> team1Image.setImageResource(R.drawable.doosan_logo)
            "KIA" -> team1Image.setImageResource(R.drawable.kia_logo)
            "롯데" -> team1Image.setImageResource(R.drawable.lotte_logo)
            "삼성" -> team1Image.setImageResource(R.drawable.samsung_logo)
            "한화" -> team1Image.setImageResource(R.drawable.hanwha_logo)
            "키움" -> team1Image.setImageResource(R.drawable.kiwoom_logo)
        }

        when(game.team2){
            "LG" ->team2Image.setImageResource(R.drawable.lg_logo)
            "KT" -> team2Image.setImageResource(R.drawable.kt_logo)
            "SSG" ->team2Image.setImageResource(R.drawable.ssg_logo)
            "NC" -> team2Image.setImageResource(R.drawable.nc_logo)
            "두산" -> team2Image.setImageResource(R.drawable.doosan_logo)
            "KIA" -> team2Image.setImageResource(R.drawable.kia_logo)
            "롯데" -> team2Image.setImageResource(R.drawable.lotte_logo)
            "삼성" -> team2Image.setImageResource(R.drawable.samsung_logo)
            "한화" -> team2Image.setImageResource(R.drawable.hanwha_logo)
            "키움" -> team2Image.setImageResource(R.drawable.kiwoom_logo)
        }

        if(game.team1Score=="none" || game.team2Score=="none"){
            team1Score.visibility=View.GONE
            team2Score.visibility=View.GONE
        }
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