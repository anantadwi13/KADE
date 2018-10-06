package com.anantadwi13.footballmatch.main

import android.net.Uri
import com.anantadwi13.footballmatch.BuildConfig
import com.anantadwi13.footballmatch.TestContextProvider
import com.anantadwi13.footballmatch.api.API
import com.anantadwi13.footballmatch.model.Match
import com.anantadwi13.footballmatch.model.MatchResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Mock
    private lateinit var api: API

    @Mock
    private lateinit var view: MainView

    @Mock
    private lateinit var gson:Gson

    private lateinit var mainPresenter: MainPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(view,api,TestContextProvider())
    }

    @Test
    fun getLastMatchList() {
        val uri = BuildConfig.BASE_URL+"/api/v1/json/"+BuildConfig.TSDB_API_KEY+"/eventspastleague.php?id=4328"

        val matches : MutableList<Match> = mutableListOf()
        val response = MatchResponse(matches)

        Mockito.`when`(gson.fromJson(api.doRequest(uri), MatchResponse::class.java)).thenReturn(response)

        mainPresenter.getLastMatchList()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(matches)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getNextMatchList() {
        val matches = api.getLastMatches(4328)

        mainPresenter.getNextMatchList()
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(matches)
        Mockito.verify(view).hideLoading()
    }
}