package com.example.testfdj.search.detail

import com.example.testfdj.injection.search.DaggerSearchComponent
import com.example.testfdj.search.data.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTeamDetailPresenterImpl(private val view: ISearchTeamDetailView): ISearchTeamDetailPresenter {

    @Inject
    lateinit var searchRepository: SearchRepository

    private var job: Job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    init {
        DaggerSearchComponent.create().inject(this)
    }

    override fun getTeamDetailed(chosenTeam: String) {
        scope.launch {
            val listOfTeams = if (::searchRepository.isInitialized) searchRepository.getTeamDetailsByName(chosenTeam) else null
            listOfTeams?.let {
                it.teams?.let { teams ->
                    if (teams.isNotEmpty())
                        view.displayTeamDetails(teams[0])
                }
            }
        }
    }
}