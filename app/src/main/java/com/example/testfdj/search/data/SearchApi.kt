package com.example.testfdj.search.data

import com.example.testfdj.search.model.Leagues
import com.example.testfdj.search.model.Teams
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("all_leagues.php")
    suspend fun getAllLeagues(): Leagues?

    @GET("search_all_teams.php")
    suspend fun getLeagueTeamsByName(@Query("l") leagueName: String): Teams?

    @GET("searchteams.php")
    suspend fun getTeamDetailsByName(@Query("t") teamName: String): Teams?
}