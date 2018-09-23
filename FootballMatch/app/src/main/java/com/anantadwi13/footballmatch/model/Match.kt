package com.anantadwi13.footballmatch.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
        @SerializedName("idEvent")
        var matchId:Int? = null,
        @SerializedName("strHomeTeam")
        var homeTeam:String? = null,
        @SerializedName("strAwayTeam")
        var awayTeam:String? = null,
        @SerializedName("idHomeTeam")
        var idHomeTeam:Int,
        @SerializedName("idAwayTeam")
        var idAwayTeam:Int,
        @SerializedName("intHomeScore")
        var homeScore:Int? = null,
        @SerializedName("intAwayScore")
        var awayScore:Int? = null,
        @SerializedName("intHomeShots")
        var homeShots:Int? = null,
        @SerializedName("intAwayShots")
        var awayShots:Int? = null,
        @SerializedName("strDate")
        var date:String? = null,
        @SerializedName("strTime")
        var time:String? = null
) : Parcelable