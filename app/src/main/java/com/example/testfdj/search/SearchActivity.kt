package com.example.testfdj.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testfdj.R
import com.example.testfdj.search.detail.SearchTeamDetailFragment
import com.example.testfdj.search.home.SearchFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        openSearchFragment()
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content,
                SearchFragment.newInstance()
            )
            .commitAllowingStateLoss()
    }

    fun openDetailFragment(chosenTeam: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.content,
                SearchTeamDetailFragment.newInstance(chosenTeam)
            )
            .addToBackStack("detail")
            .commitAllowingStateLoss()
    }
}