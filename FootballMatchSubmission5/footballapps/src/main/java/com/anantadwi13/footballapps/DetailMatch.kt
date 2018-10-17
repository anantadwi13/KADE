package com.anantadwi13.footballapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.anantadwi13.footballapps.api.API
import com.anantadwi13.footballapps.db.MatchFav
import com.anantadwi13.footballapps.model.Match
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread

class DetailMatch : AppCompatActivity() {
    private lateinit var match: Match
    private var menu: Menu? = null
    companion object {
        val RESULT_CODE = 1020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        match= intent.getParcelableExtra("dataMatch")

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        this.menu = menu
        setFavIcon(isFav())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFav()){
                    MatchFav.deleteByMatchId(applicationContext, match.matchId)
                    snackbar(homeName,"Removed from favorite!")
                    setFavIcon(false)
                }
                else{
                    MatchFav.insert(applicationContext, match)
                    snackbar(homeName,"Added to favorite!")
                    setFavIcon(true)
                }
                setResult(RESULT_CODE,intentFor<MainActivity>("changed" to true))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isFav():Boolean{
        return MatchFav.getByMatchId(applicationContext, match.matchId)!=null
    }

    private fun setFavIcon(fav: Boolean){
        if (fav){
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_fav)
        }else{
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)
        }
    }
}
