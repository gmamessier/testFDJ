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

    private lateinit var searchPresenter: SearchPresenter
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var view: ISearchView

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAllLeaguesSuccess() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.searchRepository = searchRepository
        listOfLeagues = Leagues(leaguesSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getAllLeagues()).thenReturn(listOfLeagues)
            searchPresenter.getAllLeagues()
            verify(view, times(1)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun getAllLeaguesError() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.searchRepository = searchRepository
        listOfLeagues = Leagues(leaguesSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getAllLeagues()).thenReturn(null)
            searchPresenter.getAllLeagues()
            verify(view, times(0)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun getAllLeaguesNoLeagues() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.searchRepository = searchRepository
        listOfLeagues = Leagues(leaguesError)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getAllLeagues()).thenReturn(listOfLeagues)
            searchPresenter.getAllLeagues()
            verify(view, times(0)).initLeaguesListAdapter(leaguesSuccess)
        }
    }

    @Test
    fun onLeagueChosenSuccess() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.searchRepository = searchRepository
        listOfTeams = Teams(teamsSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getLeagueTeamsByName("French Ligue 1")).thenReturn(listOfTeams)
            searchPresenter.onLeagueChosen("French Ligue 1")
            verify(view, times(1)).initTeamsListAdapter(teamsSuccess)
        }
    }

    @Test
    fun onLeagueChosenNoTeams() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.searchRepository = searchRepository
        listOfTeams = Teams(teamsError)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getLeagueTeamsByName("F")).thenReturn(listOfTeams)
            searchPresenter.onLeagueChosen("F")
            verify(view, times(0)).initTeamsListAdapter(teamsSuccess)
        }
    }

    @Test
    fun onQueryTextChangeLeaguesFound() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("ligue")
        verify(view, times(1)).onQueryTextChange(leaguesSuccess)
    }

    @Test
    fun onQueryTextChangeNoLeaguesFound() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("liga")
        verify(view, times(1)).onQueryTextChange(listOf())
    }

    @Test
    fun onQueryTextChangeHide() {
        searchPresenter = SearchPresenter(view)
        searchPresenter.setLeagues(leaguesSuccess)

        searchPresenter.onQueryTextChange("li")
        verify(view, times(1)).hideLeagueList()
    }
}