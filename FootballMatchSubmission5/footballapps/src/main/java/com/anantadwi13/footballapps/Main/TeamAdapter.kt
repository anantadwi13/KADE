package com.anantadwi13.footballapps.Main

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.model.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter (val context: Context, var teams: List<Team>, val listener: (Team) -> Unit): RecyclerView.Adapter<TeamAdapter.MainViewHolder>(){

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamBadge: ImageView = itemView.find(R.id.teamBadge)
        private val teamName: TextView = itemView.find(R.id.teamName)
        private val cardView: CardView = itemView.find(R.id.cardview)

        fun bindItem(context: Context, team: Team, listener: (Team) -> Unit){
            Glide.with(context).load(team.teamBadge)
                    .apply(RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                    .into(teamBadge)
            teamName.text = team.teamName
            cardView.onClick {
                listener(team)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
            = MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team,parent,false))

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(context, teams[position], listener)
    }

    fun setData(teams: List<Team>){
        val team:MutableList<Team> = mutableListOf()
        for (x in teams){
            if (x.sportType.equals("Soccer"))
                team.add(x)
        }
        this.teams = team
        notifyDataSetChanged()
    }

}