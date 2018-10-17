package com.anantadwi13.footballapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.widget.SearchView
import com.anantadwi13.footballapps.Main.FragmentFavorite
import com.anantadwi13.footballapps.Main.FragmentMatch
import com.anantadwi13.footballapps.Main.FragmentTeams
import com.anantadwi13.footballapps.R.id.*
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {
    private lateinit var botnav:BottomNavigationView
    private var searchView: SearchView? = null
    private var searchItem: MenuItem? = null
    var TAB_ITEM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botnav = find(R.id.botNavView)

        botnav.setOnNavigationItemSelectedListener { it ->
            when(it.itemId){
                matches ->{
                    fragmentMatch(savedInstanceState)
                }
                teams->{
                    fragmentTeams(savedInstanceState)
                }
                favorites->{
                    fragmentFavorites(savedInstanceState)
                }
            }
            true
        }

        botnav.selectedItemId = matches
    }

    fun fragmentMatch(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, FragmentMatch(), FragmentMatch::class.java.simpleName)
                    .commit()
        }
    }

    fun fragmentTeams(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, FragmentTeams(), FragmentTeams::class.java.simpleName)
                    .commit()
        }
    }

    fun fragmentFavorites(savedInstanceState: Bundle?){
        if (savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, FragmentFavorite(), FragmentFavorite::class.java.simpleName)
                    .commit()
        }
    }
}
