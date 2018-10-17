package com.anantadwi13.footballmatch.api

import android.net.Uri
import com.anantadwi13.footballmatch.*
import com.anantadwi13.footballmatch.model.Match
import com.anantadwi13.footballmatch.model.MatchResponse
import com.anantadwi13.footballmatch.model.Team
import com.anantadwi13.footballmatch.model.TeamResponse
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