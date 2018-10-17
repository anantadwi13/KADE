package com.anantadwi13.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.anantadwi13.footballapps.model.Team
import org.jetbrains.anko.db.*

class TeamFav{
    companion object {
        const val TABLE_TEAM_FAV:String = "TABLE_TEAM_FAV"
        const val TEAM_ID:String = "TEAM_ID"
        const val TEAM_NAME:String = "TEAM_NAME"
        const val TEAM_BADGE:String = "TEAM_BADGE"
        const val TEAM_FAN_ART:String = "TEAM_FAN_ART"
        const val TEAM_DESCRIPTION:String = "TEAM_DESCRIPTION"
        const val SPORT_TYPE:String = "SPORT_TYPE"

        fun insert(context: Context,team: Team){
            try {
                context.applicationContext.database.use{
                    insert(TeamFav.TABLE_TEAM_FAV,
                            TeamFav.TEAM_ID to team.teamId,
                            TeamFav.TEAM_NAME to team.teamName,
                            TeamFav.TEAM_DESCRIPTION to team.teamDescription,
                            TeamFav.TEAM_BADGE to team.teamBadge,
                            TeamFav.TEAM_FAN_ART to team.teamFanArt,
                            TeamFav.SPORT_TYPE to team.sportType)
                }
            }catch (e : SQLiteConstraintException){
                Log.e("Error DB","Error DB: error inserting team")
            }
        }
        fun deleteByTeamId(context: Context, teamId: Int){
            try {
                context.applicationContext.database.use {
                    delete(TeamFav.TABLE_TEAM_FAV,TeamFav.TEAM_ID+" = {id}", "id" to teamId.toString())
                }
            }catch (e : SQLiteConstraintException){
                error(e.localizedMessage)
            }
        }

        fun getByTeamId(context: Context, teamId: Int): Team? {
            var team: MutableList<Team> = mutableListOf()
            context.applicationContext.database.use {
                team.addAll(select(TABLE_TEAM_FAV)
                        .whereArgs(TeamFav.TEAM_ID+" = {id}", "id" to teamId.toString())
                        .parseList(classParser()))
            }
            if (team.size>0)
                return team[0]
            return null
        }

        fun getAll(context: Context): MutableList<Team> {
            val data:MutableList<Team> = mutableListOf()
            context.applicationContext.database.use {
                data.addAll(select(TABLE_TEAM_FAV).parseList(classParser()))
            }
            return data
        }
    }
}