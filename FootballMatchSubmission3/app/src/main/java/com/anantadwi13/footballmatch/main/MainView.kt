package com.anantadwi13.footballmatch.main

import com.anantadwi13.footballmatch.model.Match
import com.anantadwi13.footballmatch.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}