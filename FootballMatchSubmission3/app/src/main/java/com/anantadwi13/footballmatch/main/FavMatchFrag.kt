package com.anantadwi13.footballmatch.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anantadwi13.footballmatch.R
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.detail.DetailMatch
import com.anantadwi13.footballmatch.model.Match
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class FavMatchFrag: Fragment(), MainView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var mainPresenter: MainPresenter
    val REQUEST_CODE = 100

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMatchList(data: List<Match>) {
        adapter.setData(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI().createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list: MutableList<Match> = mutableListOf()
        swipeRefreshLayout = find(R.id.swipeRefresh)
        adapter = MainAdapter(ctx,list){
            startActivityForResult(intentFor<DetailMatch>("dataMatch" to it),REQUEST_CODE)
        }

        recyclerView = find(R.id.recyclerview)

        recyclerView.adapter = adapter

        mainPresenter = MainPresenter(this)

        swipeRefreshLayout.onRefresh {
            mainPresenter.getFavMatchList(ctx)
        }
        mainPresenter.getFavMatchList(ctx)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == DetailMatch.RESULT_CODE && data!=null){
            if(data.getBooleanExtra("changed",false)){
                mainPresenter.getFavMatchList(ctx)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    class UI: AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            linearLayout {
                backgroundColor = Color.rgb(224,224,224)
                orientation = LinearLayout.VERTICAL
                swipeRefreshLayout {
                    id = R.id.swipeRefresh
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)
                    recyclerView {
                        id = R.id.recyclerview
                        lparams(width = matchParent, height = matchParent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }.lparams(width= matchParent,height = dip(0), weight = 1f)
            }
        }
    }
}