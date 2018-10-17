package com.anantadwi13.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        @SerializedName("idTeam")
        var teamId: Int,
        @SerializedName("strTeam")
        var teamName: String? = null,
        @SerializedName("strDescriptionEN")
        var teamDescription: String? = null,
        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,
        @SerializedName("strTeamFanart1")
        var teamFanArt: String? = null,
        @SerializedName("strSport")
        var sportType: String? = null
) : Parcelable