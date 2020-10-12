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
class SearchTeamDetailPresenterImplTest {

    private val teamsSuccess = listOf(Team(0, "", "", "", "", "", ""))
    private val teamsError = null
    private lateinit var listOfTeams: Teams
    private lateinit var searchTeamDetailPresenterImpl: SearchTeamDetailPresenterImpl
    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var searchRepo: SearchRepository

    @Mock
    private lateinit var view: ISearchTeamDetailView

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        searchTeamDetailPresenterImpl = SearchTeamDetailPresenterImpl(view).apply {
            searchRepository = searchRepo
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTeamDetailedSuccess() {
        listOfTeams = Teams(teamsSuccess)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getTeamDetailsByName("Paris SG")).thenReturn(listOfTeams)
            searchTeamDetailPresenterImpl.getTeamDetailed("Paris SG")
            verify(view, times(1)).displayTeamDetails(teamsSuccess[0])
        }
    }

    @Test
    fun getTeamDetailedFailed() {
        listOfTeams = Teams(teamsError)

        dispatcher.runBlockingTest {
            `when`(searchRepo.getTeamDetailsByName("Par")).thenReturn(listOfTeams)
            searchTeamDetailPresenterImpl.getTeamDetailed("Par")
            verify(view, times(0)).displayTeamDetails(teamsSuccess[0])
        }
    }
}