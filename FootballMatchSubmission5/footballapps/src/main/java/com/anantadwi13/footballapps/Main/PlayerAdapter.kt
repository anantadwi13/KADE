package com.anantadwi13.footballapps.Main

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.model.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter (val context: Context, var player: List<Player>, val listener: (Player) -> Unit): RecyclerView.Adapter<PlayerAdapter.MainViewHolder>(){

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerCutout: ImageView = itemView.find(R.id.playerCutout)
        private val playerName: TextView = itemView.find(R.id.playerName)
        private val playerPosition: TextView = itemView.find(R.id.playerPosition)
        private val container: ConstraintLayout = itemView.find(R.id.container)

        fun bindItem(context: Context, player: Player, listener: (Player) -> Unit){
            Glide.with(context).load(player.playerCutout)
                    .apply(RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                    .into(playerCutout)
            playerName.text = player.playerName
            playerPosition.text = player.playerPosition
            container.onClick {
                listener(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
            = MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player,parent,false))

    override fun getItemCount(): Int = player.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(context, player[position], listener)
    }

    fun setData(players: List<Player>){
        this.player = players
        notifyDataSetChanged()
    }

}