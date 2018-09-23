package com.anantadwi13.myapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tenant(var id: Int, var name:String, var email:String) : Parcelable