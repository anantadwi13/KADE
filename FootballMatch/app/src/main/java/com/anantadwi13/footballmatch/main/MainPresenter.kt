package com.anantadwi13.footballmatch.main

import com.anantadwi13.footballmatch.api.API
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView) {
    fun getLastMatchList(){
        view.showLoading()
        doAsync {
            val matches = API().getLastMatches(4328)

            uiThread {
                view.hideLoading()
                view.showMatchList(matches)
            }
        }
    }
    fun getNextMatchList(){
        view.showLoading()
        doAsync {
            val matches = API().getNextMatches(4328)

            uiThread {
                view.hideLoading()
                view.showMatchList(matches)
            }
        }
    }
}