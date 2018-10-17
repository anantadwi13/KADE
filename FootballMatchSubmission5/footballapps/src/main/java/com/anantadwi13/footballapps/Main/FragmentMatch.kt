package com.anantadwi13.footballapps.Main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.SearchView
import com.anantadwi13.footballapps.DetailMatch
import com.anantadwi13.footballapps.R
import com.anantadwi13.footballapps.api.API
import com.anantadwi13.footballapps.model.Match
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClose
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.sdk25.coroutines.onSearchClick
import org.jetbrains.anko.support.v4.*

class FragmentMatch:Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: MatchAdapter
    private var searchView:SearchView?=null
    private var TAB_POS = 0
    val REQUEST_CODE = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_match,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list: MutableList<Match> = mutableListOf()
        tabLayout = find(R.id.tabLayout)
        swipeRefreshLayout = find(R.id.swiperefresh)
        adapter = MatchAdapter(ctx,list){
            startActivityForResult(intentFor<DetailMatch>("dataMatch" to it),REQUEST_CODE)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                TAB_POS = p0.position
                getMatchList()
            }
        })

        recyclerView = find(R.id.recyclerview)



        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.onRefresh {
            getMatchList()
        }
        getMatchList()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main_menu,menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView?

        searchView?.queryHint = "Find Matches"

        searchView?.onQueryTextListener {
            onQueryTextChange { query->
                if (query!=null && searchView?.isIconified==false) {
                    if (query.isNotEmpty())
                        searchMatchList(query.toString())
                    else
                        adapter.setData(listOf())
                    tabLayout.visibility = View.GONE
                }
                true
            }
        }

        searchView?.onSearchClick {
            adapter.setData(listOf())
            tabLayout.visibility = View.GONE
        }

        searchView?.onClose {
            tabLayout.visibility = View.VISIBLE
            getMatchList()
        }
    }

    fun searchMatchList(query:String){
        showLoading()
        doAsync {
            val matches = API().searchMatches(query)
            onUiThread {
                showMatchList(matches)
                hideLoading()
            }
        }
    }

    fun getMatchList(){
        showLoading()
        doAsync {
            val matches: List<Match>
            if (TAB_POS==0)
                matches = API().getNextMatches(4328)
            else
                matches = API().getLastMatches(4328)
            onUiThread {
                showMatchList(matches)
                hideLoading()
            }
        }
    }

    fun showMatchList(matches:List<Match>?){
        var match:List<Match> = mutableListOf()
        if (matches != null)
            match = matches
        adapter.setData(match)
    }

    fun showLoading(){
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideLoading(){
        swipeRefreshLayout.isRefreshing = false
    }
}