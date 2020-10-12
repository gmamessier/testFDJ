package com.example.testfdj.search.data

import com.example.testfdj.search.model.Leagues
import com.example.testfdj.search.model.Teams
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("api/v1/json/1/all_leagues.php")
    suspend fun getAllLeagues(): Leagues?

    @GET("api/v1/json/1/search_all_teams.php")
    suspend fun getLeagueTeamsByName(@Query("l") leagueName: String): Teams?

    @GET("api/v1/json/1/searchteams.php")
    suspend fun getTeamDetailsByName(@Query("t") teamName: String): Teams?
}