package com.anantadwi13.footballclub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity() {
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = DetailActivityUI().setContentView(this)

        val item: Club = intent.getParcelableExtra("item")
        val tv = view.findViewById<TextView>(DetailActivityUI.textID)
        val imgv = view.findViewById<ImageView>(DetailActivityUI.imageID)

        tv.text = item.nama
        Glide.with(this).load(item.image).into(imgv)
    }

    class DetailActivityUI: AnkoComponent<DetailActivity>{
        companion object {
            val textID = 10
            val imageID = 11
        }

        override fun createView(ui: AnkoContext<DetailActivity>): View = with(ui){
            linearLayout{
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER

                imageView{
                    id = imageID
                    adjustViewBounds = true
                }.lparams{
                    width = dip(56)
                    height = dip(56)
                    bottomMargin = dip(8)
                }

                textView{
                    id = textID
                    textSize = 18f
                }.lparams(width= wrapContent,height = wrapContent)
            }
        }
    }
}
