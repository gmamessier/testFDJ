package com.example.testfdj.search.detail

import com.example.testfdj.search.data.SearchRepository
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
class SearchTeamDetailPresenterTest {

    private val teamsSuccess = listOf(Team(0, "", "", "", "", "", ""))
    private val teamsError = null
    private lateinit var listOfTeams: Teams
    private lateinit var searchTeamDetailPresenter: SearchTeamDetailPresenter
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var view: ISearchTeamDetailView

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTeamDetailedSuccess() {
        searchTeamDetailPresenter = SearchTeamDetailPresenter(view)
        searchTeamDetailPresenter.searchRepository = searchRepository
        listOfTeams = Teams(teamsSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getTeamDetailsByName("Paris SG")).thenReturn(listOfTeams)
            searchTeamDetailPresenter.getTeamDetailed("Paris SG")
            verify(view, times(1)).displayTeamDetails(teamsSuccess[0])
        }
    }

    @Test
    fun getTeamDetailedFailed() {
        searchTeamDetailPresenter = SearchTeamDetailPresenter(view)
        searchTeamDetailPresenter.searchRepository = searchRepository
        listOfTeams = Teams(teamsError)

        dispatcher.runBlockingTest {
            `when`(searchRepository.getTeamDetailsByName("Par")).thenReturn(listOfTeams)
            searchTeamDetailPresenter.getTeamDetailed("Par")
            verify(view, times(0)).displayTeamDetails(teamsSuccess[0])
        }
    }
}