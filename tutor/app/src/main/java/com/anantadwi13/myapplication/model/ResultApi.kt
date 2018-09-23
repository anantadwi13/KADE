package com.anantadwi13.myapplication.model

data class ResultApi(var status: String, var code: Int, var data: Data){
    data class Data(var tenant:Tenant)
}