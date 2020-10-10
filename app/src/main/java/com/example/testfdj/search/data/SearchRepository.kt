package com.example.testfdj.search.data

import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchApi: SearchApi) {
    suspend fun getAllLeagues() = searchApi.getAllLeagues()

    suspend fun getLeagueTeamsByName(leagueName: String) = searchApi.getLeagueTeamsByName(leagueName)

    suspend fun getTeamDetailsByName(teamName: String) = searchApi.getTeamDetailsByName(teamName)
}