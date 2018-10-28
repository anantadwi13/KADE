package com.anantadwi13.footballapps.Main

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.model.Match
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter (val context: Context, var matches: List<Match>, val listener: (Match) -> Unit): RecyclerView.Adapter<MatchAdapter.MainViewHolder>(){

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeName: TextView = itemView.find(R.id.homeName)
        private val awayName: TextView = itemView.find(R.id.awayName)
        private val homeScore: TextView = itemView.find(R.id.homeScore)
        private val awayScore: TextView = itemView.find(R.id.awayScore)
        private val matchDate: TextView = itemView.find(R.id.date)
        private val matchTime: TextView = itemView.find(R.id.time)
        private val cardView: CardView = itemView.find(R.id.cardview)

        fun toGMTFormat(date: String, time: String): String {

            val formatter = SimpleDateFormat("yy-MM-dd HH:mm:ss")
            val datetext = SimpleDateFormat("dd-MM-yy\nHH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return datetext.format(formatter.parse(dateTime))
        }

        fun bindItem(context: Context, match: Match, listener: (Match) -> Unit){
            /*val time = match.time?.split(":")
            var timeString = ""
            if (time!=null) {
                timeString = time[0] + ":" + time[1]
            }*/
            homeName.text = match.homeTeam
            awayName.text = match.awayTeam
            homeScore.text = match.homeScore?.toString()
            awayScore.text = match.awayScore?.toString()
            matchDate.text = toGMTFormat(match.date!!, match.time!!)
            matchTime.visibility = View.GONE
            //matchTime.text = timeString
            cardView.onClick {
                listener(match)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
            = MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match,parent,false))

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(context, matches[position], listener)
    }

    fun setData(matches: List<Match>){
        val match:MutableList<Match> = mutableListOf()
        for (x in matches){
            if (x.sportType.equals("Soccer"))
                match.add(x)
        }
        this.matches = match
        notifyDataSetChanged()
    }

}