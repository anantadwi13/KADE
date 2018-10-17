package com.anantadwi13.footballapps

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.anantadwi13.footballapps.Main.PlayerAdapter
import com.anantadwi13.footballapps.api.API
import com.anantadwi13.footballapps.db.TeamFav
import com.anantadwi13.footballapps.model.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor

class DetailTeam : AppCompatActivity() {
    private lateinit var team: Team
    private var menu: Menu? = null
    private var TAB_POS = 0
    companion object {
        val RESULT_CODE = 1020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        team= intent.getParcelableExtra("dataTeam")

        supportActionBar?.title = team.teamName
        teamDescription?.text = team.teamDescription

        Glide.with(this).load(team.teamBadge)
                .apply(RequestOptions().error(R.drawable.no_image))
                .into(teamBadge)
        Glide.with(this).load(team.teamFanArt)
                .into(background)

        recyclerview?.layoutManager = LinearLayoutManager(this)

        doAsync {
            val player= API().getPlayerList(team.teamId)
            runOnUiThread {
                recyclerview?.adapter = PlayerAdapter(this@DetailTeam,player){
                    startActivity(intentFor<DetailPlayer>("dataPlayer" to it))
                }
            }
        }


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                TAB_POS = p0.position
                switchShow()
            }
        })
        switchShow()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        this.menu = menu
        setFavIcon(isFav())
        return true
    }

    private fun isFav():Boolean{
        return TeamFav.getByTeamId(applicationContext, team.teamId)!=null
    }

    private fun setFavIcon(fav: Boolean){
        if (fav){
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_fav)
        }else{
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFav()){
                    TeamFav.deleteByTeamId(applicationContext, team.teamId)
                    snackbar(teamBadge,"Removed from favorite!")
                    setFavIcon(false)
                }
                else{
                    TeamFav.insert(applicationContext, team)
                    snackbar(teamBadge,"Added to favorite!")
                    setFavIcon(true)
                }
                setResult(DetailTeam.RESULT_CODE,intentFor<MainActivity>("changed" to true))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun switchShow(){
        if (TAB_POS==0) {
            containerOverview?.visibility = View.VISIBLE
            recyclerview?.visibility =View.GONE
        }
        else{
            containerOverview?.visibility = View.GONE
            recyclerview?.visibility =View.VISIBLE
        }
    }
}
