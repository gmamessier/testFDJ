package com.example.testfdj.search.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testfdj.GlideApp
import com.example.testfdj.R
import com.example.testfdj.search.model.Team
import kotlinx.android.synthetic.main.fragment_search_team_detail.*

class SearchTeamDetailFragment : Fragment(), ISearchTeamDetailView {
    private val presenter = SearchTeamDetailPresenter(this)

    companion object {
        private const val CHOSEN_TEAM = "CHOSEN_TEAM"

        fun newInstance(chosenTeam: String): SearchTeamDetailFragment {
            val fragment = SearchTeamDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(CHOSEN_TEAM, chosenTeam)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(CHOSEN_TEAM)?.let { chosenTeam ->
            title.text = chosenTeam
            presenter.getTeamDetailed(chosenTeam)
        }

        back.setOnClickListener { activity?.onBackPressed() }
    }

    override fun displayTeamDetails(team: Team) {
        GlideApp.with(this)
            .load(team.banner)
            .into(banner)

        country.text = team.country
        league.text = team.league
        description.text = team.description
    }
}