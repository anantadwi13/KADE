package com.anantadwi13.footballapps

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.anantadwi13.footballapps.model.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_player.*
import kotlinx.android.synthetic.main.content_detail_player.*

class DetailPlayer : AppCompatActivity() {
    private lateinit var player:Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player = intent.getParcelableExtra("dataPlayer")

        supportActionBar?.title = player.playerName

        Glide.with(this).load(player.playerFanArt)
                .into(background)

        playerDescription?.text = player.playerDescription
        playerPosition?.text = player.playerPosition
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
