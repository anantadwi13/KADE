package com.anantadwi13.footballmatch.main

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.R
import com.anantadwi13.footballmatch.R.color.colorAccent
import com.anantadwi13.footballmatch.model.Match
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView, AnkoLogger {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainPresenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var lastMatchBtn: TextView
    private lateinit var nextMatchBtn: TextView
    private var mode: Int = 1

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMatchList(data: List<Match>) {
        adapter.setData(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI().setContentView(this)
        recyclerView = find(R.id.recyclerview)
        swipeRefreshLayout = find(R.id.swipeRefresh)
        adapter = MainAdapter(applicationContext,mutableListOf())
        lastMatchBtn = find(R.id.lastMatch)
        nextMatchBtn = find(R.id.nextMatch)

        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        mainPresenter = MainPresenter(this)

        mainPresenter.getLastMatchList()

        swipeRefreshLayout.onRefresh {
            if (mode==1)
                mainPresenter.getLastMatchList()
            else
                mainPresenter.getNextMatchList()
        }

        lastMatchBtn.onClick {
            if (mode!=1) {
                mainPresenter.getLastMatchList()
                nextMatchBtn.backgroundColor = Color.argb(0,255,255,255)
                lastMatchBtn.backgroundColor = Color.rgb(200,200,200)
                mode = 1
            }
        }
        nextMatchBtn.onClick {
            if (mode!=2) {
                mainPresenter.getNextMatchList()
                lastMatchBtn.backgroundColor = Color.argb(0,255,255,255)
                nextMatchBtn.backgroundColor = Color.rgb(200,200,200)
                mode = 2
            }
        }
    }

    class MainUI:AnkoComponent<MainActivity>{
        override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
            linearLayout {
                backgroundColor = Color.rgb(224,224,224)
                orientation = LinearLayout.VERTICAL
                swipeRefreshLayout {
                    id = R.id.swipeRefresh
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)
                    recyclerView {
                        id = R.id.recyclerview
                        lparams(width = matchParent, height = matchParent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }.lparams(width= matchParent,height = dip(0), weight = 1f)

                linearLayout {
                    backgroundColor = Color.rgb(255,255,255)
                    lparams(height = dip(48),width = matchParent)

                    textView("LAST MATCH"){
                        id = R.id.lastMatch
                        backgroundColor = Color.rgb(200,200,200)
                        gravity = Gravity.CENTER
                    }.lparams(height = matchParent,width = dip(0),weight = 1f)
                    textView("NEXT MATCH"){
                        id = R.id.nextMatch
                        gravity = Gravity.CENTER
                    }.lparams(height = matchParent,width = dip(0),weight = 1f)
                }
            }
        }
    }
}
