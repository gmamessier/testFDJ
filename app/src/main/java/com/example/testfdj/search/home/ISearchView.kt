package com.example.testfdj.search.home

import com.example.testfdj.search.model.League
import com.example.testfdj.search.model.Team

interface ISearchView {
    fun initLeaguesListAdapter(leagues: List<League>)
    fun initTeamsListAdapter(teams: List<Team>)
    fun onQueryTextChange(leagues: List<League>)
    fun showTeamDetail(chosenTeam: String)
    fun hideLeagueList()
}