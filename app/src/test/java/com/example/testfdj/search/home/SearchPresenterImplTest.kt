package com.example.testfdj.search.home

import com.example.testfdj.search.data.SearchRepository
import com.example.testfdj.search.model.League
import com.example.testfdj.search.model.Leagues
import com.example.testfdj.search.model.Team
import com.example.testfdj.search.model.Teams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SearchPresenterTest {

    private val leaguesSuccess = listOf(League(0, "Ligue", "", ""))
    private val leaguesError = null
    private lateinit var listOfLeagues: Leagues

    private val teamsSuccess = listOf(Team(0, "", "", "", "", "", ""))
    private val teamsError = null
    private lateinit var listOfTeams: Teams

    private lateinit var searchPresenter: ISearchPresenter
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var searchRepo: SearchRepository

    @Mock
    private lateinit var view: ISearchView

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        searchPresenter = SearchPresenterImpl(view).apply {
            searchRepository = searchRepo
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAllLeaguesSuccess() {
        listOfLeagues = Leagues(leaguesSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getAllLeagues()).thenReturn(listOfLeagues)
            searchPresenter.getAllLeagues()
            verify(view, times(1)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun getAllLeaguesError() {
        listOfLeagues = Leagues(leaguesSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getAllLeagues()).thenReturn(null)
            searchPresenter.getAllLeagues()
            verify(view, times(0)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun getAllLeaguesNoLeagues() {
        listOfLeagues = Leagues(leaguesError)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getAllLeagues()).thenReturn(listOfLeagues)
            searchPresenter.getAllLeagues()
            verify(view, times(0)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun onLeagueChosenSuccess() {
        listOfTeams = Teams(teamsSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getLeagueTeamsByName("French Ligue 1")).thenReturn(listOfTeams)
            searchPresenter.onLeagueChosen("French Ligue 1")
            verify(view, times(1)).initTeamsListAdapter(teamsSuccess)
        }
    }

    @Test
    fun onLeagueChosenNoTeams() {
        listOfTeams = Teams(teamsError)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getLeagueTeamsByName("F")).thenReturn(listOfTeams)
            searchPresenter.onLeagueChosen("F")
            verify(view, times(0)).initTeamsListAdapter(teamsSuccess)
        }
    }

    @Test
    fun onQueryTextChangeLeaguesFound() {
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("ligue")
        verify(view, times(1)).onQueryTextChange(leaguesSuccess)
    }

    @Test
    fun onQueryTextChangeNoLeaguesFound() {
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("liga")
        verify(view, times(1)).onQueryTextChange(listOf())
    }

    @Test
    fun onQueryTextChangeHide() {
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("li")
        verify(view, times(1)).hideLeagueList()
    }
}