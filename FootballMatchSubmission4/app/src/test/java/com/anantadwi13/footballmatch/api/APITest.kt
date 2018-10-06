package com.anantadwi13.footballmatch.api

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class APITest {
    @Mock
    private lateinit var api: API

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getTeamDetail() {
        val teamId = 133624
        api.getTeamDetail(teamId)
        Mockito.verify(api).getTeamDetail(teamId)
    }

    @Test
    fun getLastMatches() {
        val matchId = 576518
        api.getLastMatches(matchId)
        Mockito.verify(api).getLastMatches(matchId)
    }
}