package com.example.testfdj.injection.search

import com.example.testfdj.search.detail.SearchTeamDetailFragment
import com.example.testfdj.search.detail.SearchTeamDetailPresenterImpl
import com.example.testfdj.search.home.SearchFragment
import com.example.testfdj.search.home.SearchPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface SearchComponent {
    fun inject(searchPresenterImpl: SearchPresenterImpl)
    fun inject(searchTeamDetailPresenterImpl: SearchTeamDetailPresenterImpl)
}