package com.anantadwi13.footballapps.Main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import com.anantadwi13.footballapps.DetailTeam
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.api.API
import com.anantadwi13.footballapps.model.Match
import com.anantadwi13.footballapps.model.Team
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClose
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.sdk25.coroutines.onSearchClick
import org.jetbrains.anko.support.v4.*

class FragmentTeams:Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeamAdapter
    private lateinit var spinner: Spinner
    private var leagueID: Int = 4328
    val REQUEST_CODE = 1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_teams,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list: MutableList<Team> = mutableListOf()
        swipeRefreshLayout = find(R.id.swiperefresh)
        spinner = find(R.id.spinner)
        adapter = TeamAdapter(ctx,list){
            startActivityForResult(intentFor<DetailTeam>("dataTeam" to it),REQUEST_CODE)
        }

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerItemsID = resources.getIntArray(R.array.league_id)
        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        spinner.adapter = spinnerAdapter

        recyclerView = find(R.id.recyclerview)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        spinner.onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                leagueID = spinnerItemsID[i]
                getTeamList()
            }
        }
        swipeRefreshLayout.onRefresh {
            getTeamList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main_menu,menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as SearchView?

        searchView?.queryHint = "Find Teams"

        searchView?.onQueryTextListener {
            onQueryTextChange { query->
                if (query!=null && !searchView?.isIconified)
                    if (query.isNotEmpty())
                        searchTeamList(query.toString())
                    else
                        adapter.setData(listOf())
                true
            }
        }

        searchView?.onSearchClick {
            adapter.setData(listOf())
        }

        searchView?.onClose {
            getTeamList()
        }
    }

    fun searchTeamList(query:String){
        showLoading()
        doAsync {
            val teams = API().searchTeams(query)
            onUiThread {
                showTeamList(teams)
                hideLoading()
            }
        }
    }

    fun getTeamList(){
        showLoading()
        doAsync {
            val teams = API().getTeamList(leagueID)
            onUiThread {
                showTeamList(teams)
                hideLoading()
            }
        }
    }

    fun showTeamList(teams:List<Team>?){
        var team:List<Team> = mutableListOf()
        if (teams!= null)
            team = teams
        adapter.setData(team)
    }

    fun showLoading(){
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideLoading(){
        swipeRefreshLayout.isRefreshing = false
    }
}