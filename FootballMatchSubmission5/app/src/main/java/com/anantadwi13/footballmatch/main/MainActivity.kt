package com.anantadwi13.footballmatch.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anantadwi13.footballmatch.R
import com.anantadwi13.footballmatch.R.id.*
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.db.MatchFav
import com.anantadwi13.footballmatch.model.Match
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), MainView, AnkoLogger {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMatchList(data: List<Match>) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView?.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                lastMatch -> {
                    loadPrevMatchFrag(savedInstanceState)
                }
                nextMatch -> {
                    loadNextMatchFrag(savedInstanceState)
                }
                favMatch -> {
                    loadFavMatchFrag(savedInstanceState)
                }
            }
            true
        }
        bottomNavigationView.selectedItemId = lastMatch
    }

    private fun loadPrevMatchFrag(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, PrevMatchFrag(), PrevMatchFrag::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadNextMatchFrag(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, NextMatchFrag(), NextMatchFrag::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadFavMatchFrag(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, FavMatchFrag(), FavMatchFrag::class.java.simpleName)
                    .commit()
        }
    }
}
