package com.anantadwi13.footballmatch.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anantadwi13.footballmatch.R
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.model.Match
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.uiThread

class DetailMatch : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        val match:Match = intent.getParcelableExtra("dataMatch")

        date?.text = match.date
        homeName?.text = match.homeTeam
        awayName?.text = match.awayTeam
        homeScore?.text = match.homeScore?.toString()
        awayScore?.text = match.awayScore?.toString()
        homeShots?.text = match.homeShots?.toString()?:"-"
        awayShots?.text = match.awayShots?.toString()?:"-"

        doAsync {
            val api = API()
            val homeTeam = api.getTeamDetail(match.idHomeTeam)
            val awayTeam = api.getTeamDetail(match.idAwayTeam)
            uiThread {
                Glide.with(this@DetailMatch).load(homeTeam.teamBadge)
                        .apply(RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                        .into(homeBadge)
                Glide.with(this@DetailMatch).load(awayTeam.teamBadge)
                        .apply(RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                        .into(awayBadge)
            }
        }

    }
}
