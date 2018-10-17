package com.anantadwi13.footballapps.api

import android.net.Uri
import com.anantadwi13.footballapps.*
import com.anantadwi13.footballapps.model.*
import com.google.gson.Gson
import java.net.URL

class API {
    fun getTeamDetail(id: Int): Team {
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("json")
                    .appendPath(BuildConfig.TSDB_API_KEY)
                    .appendPath("lookupteam.php")
                    .appendQueryParameter("id",id.toString())
                    .build()
                    .toString()
        return Gson().fromJson<TeamResponse>(doRequest(uri), TeamResponse::class.java).teams[0]
    }

    fun getLastMatches(idLeague: Int):List<Match>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("json")
                    .appendPath(BuildConfig.TSDB_API_KEY)
                    .appendPath("eventspastleague.php")
                    .appendQueryParameter("id",idLeague.toString())
                    .build()
                    .toString()
        return Gson().fromJson<MatchResponse>(doRequest(uri), MatchResponse::class.java).events
    }
    fun getNextMatches(idLeague: Int):List<Match>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("json")
                    .appendPath(BuildConfig.TSDB_API_KEY)
                    .appendPath("eventsnextleague.php")
                    .appendQueryParameter("id",idLeague.toString())
                    .build()
                    .toString()
        return Gson().fromJson<MatchResponse>(doRequest(uri), MatchResponse::class.java).events
    }

    fun getTeamList(idLeague: Int):List<Team>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookup_all_teams.php")
                .appendQueryParameter("id",idLeague.toString())
                .build()
                .toString()
        return Gson().fromJson<TeamResponse>(doRequest(uri), TeamResponse::class.java).teams
    }

    fun getPlayerList(idTeam: Int):List<Player>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookup_all_players.php")
                .appendQueryParameter("id",idTeam.toString())
                .build()
                .toString()
        return Gson().fromJson<PlayerResponse>(doRequest(uri), PlayerResponse::class.java).player
    }

    fun searchTeams(query: String):List<Team>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("json")
                    .appendPath(BuildConfig.TSDB_API_KEY)
                    .appendPath("searchteams.php")
                    .appendQueryParameter("t",query)
                    .build()
                    .toString()
        return Gson().fromJson<TeamResponse>(doRequest(uri), TeamResponse::class.java).teams
    }

    fun searchMatches(query: String):List<Match>{
        val uri = Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchevents.php")
                .appendQueryParameter("e",query)
                .build()
                .toString()
        return Gson().fromJson<MatchResponse>(doRequest(uri), MatchResponse::class.java).event
    }

    fun doRequest(url: String):String{
        return URL(url).readText()
    }
}