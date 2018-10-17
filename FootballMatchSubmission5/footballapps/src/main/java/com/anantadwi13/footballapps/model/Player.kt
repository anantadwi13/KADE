package com.anantadwi13.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        @SerializedName("idTeam")
        var teamId: String? = null,
        @SerializedName("idPlayer")
        var playerId: String? = null,
        @SerializedName("strPlayer")
        var playerName: String? = null,
        @SerializedName("strDescriptionEN")
        var playerDescription: String? = null,
        @SerializedName("strPosition")
        var playerPosition: String? = null,
        @SerializedName("strFanart1")
        var playerFanArt: String? = null,
        @SerializedName("strCutout")
        var playerCutout: String? = null,
        @SerializedName("strSport")
        var sportType: String? = null
) : Parcelable