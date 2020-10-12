package com.example.testfdj.search.home

import com.example.testfdj.search.data.SearchRepository
import com.example.testfdj.search.injection.DaggerSearchComponent
import com.example.testfdj.search.model.League
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPresenterImpl(private val view: ISearchView): ISearchPresenter {

    @Inject
    lateinit var searchRepository: SearchRepository

    private lateinit var leagues: List<League>
    private var job: Job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    init {
        DaggerSearchComponent.create().inject(this)
    }

    override fun getAllLeagues() {
        scope.launch{
            val listOfLeagues = if (::searchRepository.isInitialized) searchRepository.getAllLeagues() else null
            listOfLeagues?.let { list ->
                list.leagues?.let {
                    setLeagues(it)
                    view.initLeaguesListAdapter(leagues)
                }
            }
        }
    }

    override fun onQueryTextChange(searchText: String) {
        if (::leagues.isInitialized && leagues.isNotEmpty() && searchText.length >= 3) {
            view.onQueryTextChange(leagues.filter {
                    league -> (league.alternateName != null && league.alternateName.contains(searchText, ignoreCase = true)) || league.name.contains(searchText, ignoreCase = true)
            })
        } else {
            view.hideLeagueList()
        }
    }

    override fun onLeagueChosen(chosenLeague: String) {
        view.hideLeagueList()

        scope.launch {
            val listOfTeams = if (::searchRepository.isInitialized) searchRepository.getLeagueTeamsByName(chosenLeague) else null
            listOfTeams?.let {
                it.teams?.let { teams ->
                    view.initTeamsListAdapter(teams)
                }
            }
        }
    }

    override fun onTeamChosen(chosenTeam: String) {
        view.showTeamDetail(chosenTeam)
    }

    override fun setLeagues(newLeagues: List<League>) {
        leagues = newLeagues
    }
}