package com.anantadwi13.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, "footballdb",null,1) {
    companion object {
        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context):DBOpenHelper{
            if (instance==null){
                instance = DBOpenHelper(context.applicationContext)
            }
            return instance as DBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MatchFav.TABLE_MATCH_FAV,true,
                //MatchFav.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                MatchFav.MATCH_ID to INTEGER + PRIMARY_KEY,
                MatchFav.HOME_TEAM to TEXT,
                MatchFav.AWAY_TEAM to TEXT,
                MatchFav.HOME_TEAM_ID to INTEGER,
                MatchFav.AWAY_TEAM_ID to INTEGER,
                MatchFav.HOME_SCORE to INTEGER,
                MatchFav.AWAY_SCORE to INTEGER,
                MatchFav.HOME_SHOTS to INTEGER,
                MatchFav.AWAY_SHOTS to INTEGER,
                MatchFav.DATE to TEXT,
                MatchFav.TIME to TEXT,
                MatchFav.SPORT_TYPE to TEXT
        )
        db.createTable(TeamFav.TABLE_TEAM_FAV,true,
                TeamFav.TEAM_ID to INTEGER + PRIMARY_KEY,
                TeamFav.TEAM_NAME to TEXT,
                TeamFav.TEAM_DESCRIPTION to TEXT,
                TeamFav.TEAM_BADGE to TEXT,
                TeamFav.TEAM_FAN_ART to TEXT,
                TeamFav.SPORT_TYPE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchFav.TABLE_MATCH_FAV,true)
        db.dropTable(TeamFav.TABLE_TEAM_FAV,true)
    }
}

val Context.database: DBOpenHelper
    get() = DBOpenHelper.getInstance(applicationContext)