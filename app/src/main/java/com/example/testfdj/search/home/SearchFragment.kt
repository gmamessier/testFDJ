package com.example.testfdj.search.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import com.example.testfdj.R
import com.example.testfdj.search.SearchActivity
import com.example.testfdj.search.home.adapter.LeaguesListAdapter
import com.example.testfdj.search.home.adapter.TeamsListAdapter
import com.example.testfdj.search.model.League
import com.example.testfdj.search.model.Team
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), ISearchView {

    private val presenter = SearchPresenter(this)
    private lateinit var leaguesListAdapter: LeaguesListAdapter
    private lateinit var teamsListAdapter: TeamsListAdapter

    companion object {
        fun newInstance(): SearchFragment =
            SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllLeagues()
        initSearchView()
    }

    private fun initSearchView() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onQueryTextChange(newText)
                return false
            }
        })
    }

    private fun getAllLeagues() {
        presenter.getAllLeagues()
    }

    override fun initLeaguesListAdapter(leagues: List<League>) {
        activity?.let {
            leaguesListAdapter = LeaguesListAdapter(leagues)
            leaguesView.adapter = leaguesListAdapter

            leaguesListAdapter.onItemClick = {
                hideKeyboard()
                searchView.setQuery(it, false)
                presenter.onLeagueChosen(it)
            }
        }
    }

    override fun initTeamsListAdapter(teams: List<Team>) {
        activity?.let {
            teamsListAdapter = TeamsListAdapter(teams)
            teamsView.adapter = teamsListAdapter

            teamsListAdapter.onItemClick = {
                presenter.onTeamChosen(it)
            }

            teamsView.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextChange(leagues: List<League>) {
        displayLeaguesList(true)
        leaguesListAdapter.updateLeaguesList(leagues)
    }
    
    override fun hideLeagueList() {
        displayLeaguesList(false)
    }
    
    private fun displayLeaguesList(shouldDisplay: Boolean) {
        leaguesView.visibility = if (shouldDisplay) View.VISIBLE else View.GONE
    }

    override fun showTeamDetail(chosenTeam: String) {
        activity?.let {
            (it as SearchActivity).openDetailFragment(chosenTeam)
        }
    }

    private fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive)
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }
}