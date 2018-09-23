package com.anantadwi13.footballmatch.main

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.anantadwi13.footballmatch.model.Match
import com.anantadwi13.footballmatch.R
import com.anantadwi13.footballmatch.detail.DetailMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainAdapter(val context: Context, var matches: List<Match>):RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val homeName: TextView = itemView.find(R.id.homeName)
        private val awayName: TextView = itemView.find(R.id.awayName)
        private val homeScore: TextView = itemView.find(R.id.homeScore)
        private val awayScore: TextView = itemView.find(R.id.awayScore)
        private val matchDate: TextView = itemView.find(R.id.matchDate)
        private val cardView: CardView = itemView.find(R.id.cardview)

        fun bindItem(context: Context, match: Match){
            homeName.text = match.homeTeam
            awayName.text = match.awayTeam
            homeScore.text = match.homeScore?.toString()
            awayScore.text = match.awayScore?.toString()
            matchDate.text = match.date
            cardView.onClick {
                //Toast.makeText(itemView.context,match.homeTeam,Toast.LENGTH_SHORT).show()
                context.startActivity<DetailMatch>("dataMatch" to match)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
            = MainViewHolder(ItemUI().createView(AnkoContext.create(parent.context,parent)))

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(context, matches[position])
    }

    fun setData(matches: List<Match>){
        this.matches = matches
        notifyDataSetChanged()
    }

}

class ItemUI:AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
        cardView {
            id = R.id.cardview
            lparams(width = matchParent, height = wrapContent) {
                margin = dip(8)
            }
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.matchDate
                    text = "20/01/2018"
                    gravity = Gravity.CENTER
                    bottomPadding = dip(8)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.homeName
                        textSize = 16f
                        text = "Everton"
                        gravity = Gravity.RIGHT + Gravity.CENTER_VERTICAL
                        rightPadding = dip(20)
                    }.lparams(width = 0, height = wrapContent, weight = 1f)
                    textView {
                        id = R.id.homeScore
                        textSize = 20f
                        text = "2"
                        gravity = Gravity.CENTER
                        rightPadding = dip(10)
                        setTypeface(typeface, Typeface.BOLD)
                    }.lparams(width = wrapContent, height = wrapContent)
                    textView {
                        textSize = 16f
                        text = "VS"
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent, height = wrapContent)
                    textView {
                        id = R.id.awayScore
                        textSize = 20f
                        text = "1"
                        gravity = Gravity.CENTER
                        leftPadding = dip(10)
                        setTypeface(typeface, Typeface.BOLD)
                    }.lparams(width = wrapContent, height = wrapContent)
                    textView {
                        id = R.id.awayName
                        textSize = 16f
                        text = "Southampton"
                        gravity = Gravity.LEFT + Gravity.CENTER_VERTICAL
                        leftPadding = dip(20)
                    }.lparams(width = 0, height = wrapContent, weight = 1f)
                }
            }
        }
    }
}