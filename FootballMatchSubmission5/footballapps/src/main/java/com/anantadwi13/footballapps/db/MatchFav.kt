package com.anantadwi13.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.anantadwi13.footballapps.model.Match
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.error

class MatchFav{
    companion object {
        const val TABLE_MATCH_FAV:String = "TABLE_MATCH_FAV"
        const val MATCH_ID:String = "MATCH_ID"
        const val HOME_TEAM:String = "HOME_TEAM"
        const val AWAY_TEAM:String = "AWAY_TEAM"
        const val HOME_TEAM_ID:String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID:String = "AWAY_TEAM_ID"
        const val HOME_SCORE:String = "HOME_SCORE"
        const val AWAY_SCORE:String = "AWAY_SCORE"
        const val HOME_SHOTS:String = "HOME_SHOTS"
        const val AWAY_SHOTS:String = "AWAY_SHOTS"
        const val DATE:String = "DATE"
        const val TIME:String = "TIME"
        const val SPORT_TYPE:String = "SPORT_TYPE"

        fun insert(context: Context,match: Match){
            try {
                context.applicationContext.database.use{
                    insert(MatchFav.TABLE_MATCH_FAV,
                            MatchFav.MATCH_ID to match.matchId,
                            MatchFav.HOME_TEAM to match.homeTeam,
                            MatchFav.AWAY_TEAM to match.awayTeam,
                            MatchFav.HOME_TEAM_ID to match.idHomeTeam,
                            MatchFav.AWAY_TEAM_ID to match.idAwayTeam,
                            MatchFav.HOME_SCORE to match.homeScore,
                            MatchFav.AWAY_SCORE to match.awayScore,
                            MatchFav.HOME_SHOTS to match.homeShots,
                            MatchFav.AWAY_SHOTS to match.awayShots,
                            MatchFav.DATE to match.date,
                            MatchFav.TIME to match.time,
                            MatchFav.SPORT_TYPE to match.sportType)
                }
            }catch (e : SQLiteConstraintException){
                Log.e("Error DB","Error DB: error inserting match")
            }
        }
        fun deleteByMatchId(context: Context, matchId: Int){
            try {
                context.applicationContext.database.use {
                    delete(MatchFav.TABLE_MATCH_FAV,MatchFav.MATCH_ID+" = {id}", "id" to matchId.toString())
                }
            }catch (e : SQLiteConstraintException){
                error(e.localizedMessage)
            }
        }

        fun getByMatchId(context: Context, matchId: Int): Match? {
            var match: MutableList<Match> = mutableListOf()
            context.applicationContext.database.use {
                match.addAll(select(TABLE_MATCH_FAV)
                        .whereArgs(MatchFav.MATCH_ID+" = {id}", "id" to matchId.toString())
                        .parseList(classParser()))
            }
            if (match.size>0)
                return match[0]
            return null
        }

        fun getAll(context: Context): MutableList<Match> {
            val data:MutableList<Match> = mutableListOf()
            context.applicationContext.database.use {
                data.addAll(select(TABLE_MATCH_FAV).parseList(classParser()))
            }
            return data
        }
    }
}