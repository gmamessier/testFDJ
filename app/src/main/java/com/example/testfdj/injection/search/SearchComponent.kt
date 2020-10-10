package com.example.testfdj.injection.search

import com.example.testfdj.search.detail.SearchTeamDetailPresenter
import com.example.testfdj.search.home.SearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface SearchComponent {
    fun inject(searchPresenter: SearchPresenter)
    fun inject(searchTeamDetailPresenter: SearchTeamDetailPresenter)
}