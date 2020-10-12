package com.example.testfdj.search.home

import com.example.testfdj.search.model.League

interface ISearchPresenter {
    fun onQueryTextChange(searchText: String)
    fun getAllLeagues()
    fun onLeagueChosen(chosenLeague: String)
    fun setLeagues(newLeagues: List<League>)
    fun onTeamChosen(chosenTeam: String)
}