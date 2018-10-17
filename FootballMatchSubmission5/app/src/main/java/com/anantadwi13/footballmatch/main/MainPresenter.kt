package com.anantadwi13.footballmatch.main

import android.content.Context
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.db.MatchFav
import com.anantadwi13.footballmatch.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView, private val api: API=API(), private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getLastMatchList(){
        view.showLoading()
        async(context.main) {
            val matches = bg {
                api.getLastMatches(4328)
            }
            view.showMatchList(matches.await())
            view.hideLoading()
        }
    }
    fun getNextMatchList(){
        view.showLoading()
        async(context.main) {
            val matches = bg {
                api.getNextMatches(4328)
            }
            view.showMatchList(matches.await())
            view.hideLoading()
        }
    }
    fun getFavMatchList(context: Context){
        view.showLoading()
        async(this.context.main) {
            val matches = bg {
                MatchFav.getAll(context)
            }
            view.showMatchList(matches.await())
            view.hideLoading()
        }
    }
}