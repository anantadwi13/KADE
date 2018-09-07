package com.anantadwi13.footballclub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        val listClub:MutableList<Club> = mutableListOf()

        val names = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        for (i in names.indices){
            listClub.add(Club(names[i], image.getResourceId(i, 0)))
        }
        image.recycle()


        recyclerview.adapter = RecyclerViewAdapter(this, listClub) {
            item ->
            if (item.nama != null) {
                Toast.makeText(this, item.nama, Toast.LENGTH_SHORT).show()
                startActivity<DetailActivity>("item" to item)
            }
        }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.hasFixedSize()

    }
}
