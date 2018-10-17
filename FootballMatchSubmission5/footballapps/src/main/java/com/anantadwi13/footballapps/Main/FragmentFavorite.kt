package com.anantadwi13.footballapps.Main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anantadwi13.footballapps.DetailMatch
import com.anantadwi13.footballapps.DetailTeam
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.api.API
import com.anantadwi13.footballapps.db.MatchFav
import com.anantadwi13.footballapps.db.TeamFav
import com.anantadwi13.footballapps.model.Team
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.*

class FragmentFavorite : Fragment() {
    var TAB_POS = 0
    val REQUEST_CODE = 1002

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(ctx)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab) {

            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                TAB_POS = p0.position
                fetch()
            }
        })
        fetch()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == DetailMatch.RESULT_CODE && data!=null){
            if(data.getBooleanExtra("changed",false)){
                refetch()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun refetch(){
        if (TAB_POS==0)
            getMatchFav()
        else
            getTeamFav()
    }

    fun fetch(){
        if (TAB_POS==0)
            modeMatch()
        else
            modeTeam()
    }

    fun modeMatch(){
        recyclerview.adapter = MatchAdapter(ctx, listOf()){
            startActivityForResult(intentFor<DetailMatch>("dataMatch" to it),REQUEST_CODE)
        }
        swiperefresh.onRefresh {
            getMatchFav()
        }
        getMatchFav()
    }
    fun getMatchFav(){
        showLoading()
        doAsync {
            val matches = MatchFav.getAll(ctx)
            onUiThread {
                (recyclerview.adapter as MatchAdapter).setData(matches)
                hideLoading()
            }
        }
    }
    fun modeTeam(){
        recyclerview.adapter = TeamAdapter(ctx, listOf()){
            startActivityForResult(intentFor<DetailTeam>("dataTeam" to it),REQUEST_CODE)
        }
        swiperefresh.onRefresh {
            getTeamFav()
        }
        getTeamFav()
    }
    fun getTeamFav(){
        showLoading()
        doAsync {
            val teams = TeamFav.getAll(ctx)
            onUiThread {
                (recyclerview.adapter as TeamAdapter).setData(teams)
                hideLoading()
            }
        }
    }
    fun showLoading(){
        swiperefresh.isRefreshing = true
    }
    fun hideLoading(){
        swiperefresh.isRefreshing = false
    }
}
